package cn.walkerl.service;

import cn.walkerl.dto.OrderDTO;

/**
 * 推送消息
 * @author WalkerL
 * @date: 2018年5月28日
 */
public interface PushMessageService {

	/**
	 * 订单状态变更消息
	 * @param orderDTO
	 */
	void orderStatus(OrderDTO orderDTO);
	
}
