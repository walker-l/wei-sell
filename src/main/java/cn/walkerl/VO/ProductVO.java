package cn.walkerl.VO;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ProductVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3590818746907937725L;

	private String categoryName;
	
	private Integer categoryType;
	
	
	private List<ProductInfoVO> productInfoVOList;
}
