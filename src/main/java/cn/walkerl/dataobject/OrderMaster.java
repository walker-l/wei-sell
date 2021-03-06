package cn.walkerl.dataobject;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import cn.walkerl.enums.OrderStatusEnum;
import cn.walkerl.enums.PayStatusEnum;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
	
	/** 订单id. */
	@Id
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
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	/** 支付状态，默认为0 未支付. */
	private Integer payStatus = PayStatusEnum.WATI.getCode();
	
	/** 创建时间. */
	private Date createTime;
			
	/** 更新时间. */
	private Date updateTime;
	
	
}
