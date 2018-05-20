package cn.walkerl.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@DynamicUpdate
@Data
public class ProductCategory {
	
	/** 类目id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	/** 类目名字. */
	private String categoryName;
	
	/** 类目编号. */
	private Integer categoryType;
	
	/** 创建时间. */
	private Date createTime;
	
	/** 更新时间. */
	private Date updateTime;
	
	/** 无参的构造方法. */
	public ProductCategory() {
		
	}
	
	/** 带参数的构造方法. */
	public ProductCategory(String categoryName, Integer categoryType) {
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}

}
