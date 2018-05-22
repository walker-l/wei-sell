package cn.walkerl.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductForm {

	// 商品id
	private String productId;
	
	//商品名称
	@NotEmpty(message = "商品名称必填")
	private String productName;
	
	//商品价格
	@NotNull(message = "商品价格必填")
	private BigDecimal productPrice;
	
	//商品库存
	@NotNull(message = "商品库存必填")
	private Integer productStock;
	
	//商品描述
	private String productDescription;
	
	//商品图片
	private String productIcon;
	
	//商品类目编号
	private Integer categoryType;
	
}
