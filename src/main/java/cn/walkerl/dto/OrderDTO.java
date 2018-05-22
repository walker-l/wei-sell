package cn.walkerl.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.walkerl.dataobject.OrderDetail;
import cn.walkerl.enums.OrderStatusEnum;
import cn.walkerl.enums.PayStatusEnum;
import cn.walkerl.utils.EnumUtil;
import cn.walkerl.utils.serializer.Date2LongSerializer;
import lombok.Data;

@Data
public class OrderDTO {

	/** 订单id. */
	private String orderId;
	
	/** 买家名字. */
	private String buyerName;

	/** 买家手机号. */
	private String buyerPhone;
	
	/** 买家地址. */
	private String buyerAddress;
	
	/** 买家微信Openid. */
	private String buyerOpenid;
	
	/** 订单总金额. */
	private BigDecimal orderAmount;
	
	/** 订单状态，默认为0 新下单. */
	private Integer orderStatus;

	/** 支付状态，默认为0 未支付. */
	private Integer payStatus;
	
	/** 创建时间. */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;
			
	/** 更新时间. */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;
	
	/* 创立orderDetailList,方便controller层使用 */
	List<OrderDetail> orderDetailList;
	
	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum () {
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}
	
	@JsonIgnore
	public PayStatusEnum getPayStatusEnum() {
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}
	
	
}
