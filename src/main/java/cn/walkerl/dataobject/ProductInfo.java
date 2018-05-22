package cn.walkerl.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.walkerl.enums.ProductStatusEnum;
import cn.walkerl.utils.EnumUtil;
import lombok.Data;

/**
 * 商品实体类
 * @author WalkerL
 * @date: 2018年4月10日
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

	@Id
	private String productId;
	
	/** 名字. */
	private String productName;
	
	/** 单价. */
	private BigDecimal productPrice;
	
	/** 库存. */
	private Integer productStock;
	
	/** 描述. */
	private String productDescription;
	
	/** 小图. */
	private String productIcon;
	
	/** 状态: 0正常，1下架. */
	private Integer productStatus = ProductStatusEnum.UP.getCode();
	
	/** 类目编号. */
	private Integer categoryType;
	
	private Date createTime;
	
	private Date updateTime;
	
	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
		return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
	}
}
