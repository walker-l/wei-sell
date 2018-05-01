package cn.walkerl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.walkerl.dataobject.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{

	
	ProductCategory findByCategoryId(Integer categpryId);
	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
