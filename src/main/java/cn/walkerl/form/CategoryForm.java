package cn.walkerl.form;

import lombok.Data;

@Data
public class CategoryForm {

	/** 类目ID. **/
	private Integer categoryId;
	
	/** 类目名称. **/
	private String categoryName;
	
	/** 类目编号. **/
	private Integer categoryType;
}
