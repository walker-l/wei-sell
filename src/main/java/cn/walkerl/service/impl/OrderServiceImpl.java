package cn.walkerl.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.walkerl.converter.OrderMaster2OrderDTOConverter;
import cn.walkerl.dataobject.OrderDetail;
import cn.walkerl.dataobject.OrderMaster;
import cn.walkerl.dataobject.ProductInfo;
import cn.walkerl.dto.CartDTO;
import cn.walkerl.dto.OrderDTO;
import cn.walkerl.enums.OrderStatusEnum;
import cn.walkerl.enums.PayStatusEnum;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.repository.OrderDetailRepository;
import cn.walkerl.repository.OrderMasterRepository;
import cn.walkerl.service.OrderService;
import cn.walkerl.service.ProductService;
import cn.walkerl.service.PushMessageService;
import cn.walkerl.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;

@Service("OrderServiceImpl")
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	/**
	 * 创建订单
	 */
	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		
		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmount = new BigDecimal(0);
		
		//1. 查询商品（数量，价格）
		for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
			ProductInfo productInfo = productService.findById(orderDetail.getProductId());
			
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			
			//2. 计算订单总价
			orderAmount = productInfo.getProductPrice()
					.multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);
			
			//订单详情入库
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			orderDetail.setOrderId(orderId);
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetailRepository.save(orderDetail);
		}
		
		
		//3. 写入订单数据库（orderMaster和orderDetail）
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WATI.getCode());
		orderMasterRepository.save(orderMaster);
		
		//4. 扣库存
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
				.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productService.decreaseStock(cartDTOList);
		
		return orderDTO;
	}

	
	/**
	 * 查找一个订单
	 */
	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
		if (orderMaster == null) {
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		
		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		
		return orderDTO;
	}

	
	/**
	 * 查找某位买家订单列表
	 */
	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		
		return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
	}

	
	/**
	 * 取消订单
	 */
	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】 订单状态不正确，orderId={}, orderStatus={}",
					orderDTO.getOrderId(), orderDTO.getOrderStatus()
					);
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		//返回库存 
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
				.stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productService.increaseStock(cartDTOList);
		
		//如果已支付，需要退款
		/** if ( orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode()) ) {
			payService.refund(orderDTO);
		} */
		return orderDTO;
	}

	
	/**
	 * 完结订单
	 */
	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		//判断订单状态
		if ( !orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ) {
			log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",
					orderDTO.getOrderId(), orderDTO.getOrderStatus()
					);
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		
		if ( updateResult == null ) {
			log.error( "【完结订单】更新失败，orderMaster={}",orderMaster );
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		
		//推送微信模板消息
		pushMessageService.orderStatus(orderDTO);
		
		return orderDTO;
	}

	
	/**
	 * 订单支付
	 */
	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		//判断订单状态
		if ( !orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ) {
			log.error("【订单支付完成】订单状态不正确，orderId={}, orderStatus={}",
					orderDTO.getOrderId(), orderDTO.getOrderStatus()
					);
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//判断支付状态
		if ( !orderDTO.getPayStatus().equals(PayStatusEnum.WATI.getCode()) ) {
			log.error("【订单支付完成】订单支付状态不正确，orderDTO={}",orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		
		//修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if ( updateResult == null ) {
			log.error("【订单支付完成】更新失败，orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		return orderDTO;
		
	}

	
	/**
	 * 卖家查找所有订单
	 */
	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		
		return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
	}
	
	
	

}
