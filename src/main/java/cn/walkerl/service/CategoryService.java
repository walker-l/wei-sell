package cn.walkerl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.walkerl.dataobject.ProductCategory;

public interface CategoryService {

	ProductCategory findOne(Integer categoryId);
	
	List<ProductCategory> findAll();
	
	public Page<ProductCategory> findAll(Pageable pageable);
	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
	
	ProductCategory save(ProductCategory productCategory);
}
