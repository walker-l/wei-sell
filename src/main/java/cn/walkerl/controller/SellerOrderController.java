package cn.walkerl.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.walkerl.dto.OrderDTO;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

	@Autowired
	@Qualifier("OrderServiceImpl")
	private OrderService orderService;
	
	/**
	 * 订单列表
	 * @param page  第几页，从1页开始
	 * @param size  一页有多少条数据 
	 * @param map
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
			                 @RequestParam(value = "size", defaultValue = "10") Integer size,
			                 Map<String, Object> map) {
		PageRequest request = PageRequest.of(page - 1, size);
		Page<OrderDTO> orderDTOPage = orderService.findList(request);
		map.put("orderDTOPage", orderDTOPage);
		map.put("currentPage", page);
		map.put("size", size);
		
		return new ModelAndView("order/list_order", map);
	}
	
	
	/**
	 * 取消订单
	 * @param orderId  订单号
	 * @param page  用户点击取消时所在的页面
	 * @param size  用户点击取消时所在的页面的数据条数
	 * @param map
	 * @return
	 */
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId,
							   @RequestParam(value = "page", defaultValue = "1") Integer page,
					           @RequestParam(value = "size", defaultValue = "10") Integer size,
					           Map<String, Object> map) {
		try {
			OrderDTO orderDTO = orderService.findOne(orderId);
			orderService.cancel(orderDTO);
		} catch (SellException e) {
			log.error("【卖家端取消订单】发生异常{}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/order/list?page=" + page + "&size=" +size);
			return new ModelAndView("common/error", map);
		}
		
		map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessge());
		map.put("url", "/wei-sell/seller/order/list?page=" + page + "&size=" +size);
		return new ModelAndView("common/success");
	}
	
	
	/**
	 * 订单详情
	 * @param orderId
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
							   @RequestParam(value = "page", defaultValue = "1") Integer page,
					           @RequestParam(value = "size", defaultValue = "10") Integer size,
					           Map<String, Object> map) {
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.findOne(orderId);
		} catch (SellException e) {
			log.error("【卖家端查询订单详情】发生异常{}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/order/list?page=" + page + "&size=" +size);
			return new ModelAndView("common/error", map);
		}
		
		map.put("orderDTO", orderDTO);
		map.put("page", page);
		map.put("size", size);
		return new ModelAndView("order/detail_order", map);
	}
	
	/**
	 * 完结订单
	 * @param orderId
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@GetMapping("/finish")
	public ModelAndView finished(@RequestParam("orderId") String orderId,
							   @RequestParam(value = "page", defaultValue = "1") Integer page,
					           @RequestParam(value = "size", defaultValue = "10") Integer size,
					           Map<String, Object> map) {
		try {
			OrderDTO orderDTO = orderService.findOne(orderId);
			orderService.finish(orderDTO);
		} catch (SellException e) {
			log.error("【卖家端完结订单】发生异常{}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/order/list?page=" + page + "&size=" +size);
			return new ModelAndView("common/error", map);
		}
		
		map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessge());
		map.put("url", "/wei-sell/seller/order/list?page=" + page + "&size=" +size);
		return new ModelAndView("common/success", map);
	}
}
