package cn.walkerl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.walkerl.dto.OrderDTO;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.service.BuyerService;
import cn.walkerl.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@Service("BuyerServiceImpl")
@Slf4j
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	@Qualifier("OrderServiceImpl")
	private OrderService orderService;
	
	@Override
	public OrderDTO findOrderOne(String openid, String orderId) {
		return checkOrderOwner(openid, orderId);
		
	}

	@Override
	public OrderDTO cancelOrder(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		if (orderDTO == null) {
			log.error("【取消订单】查不到该订单，orderId={}", orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		
		return orderService.cancel(orderDTO);
	}
	
	private OrderDTO checkOrderOwner(String openid, String orderId) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		if (orderDTO == null) {
			return null;
		}
		
		//判断是否是自己的订单
		if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
			log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}",
					openid, orderDTO);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return orderDTO;
	}

}
