package cn.walkerl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.walkerl.dataobject.ProductInfo;

public interface ProductService {

	public ProductInfo findById(String productId);
	
	/**
	 * 查询所有在架商品列表
	 * @return
	 */
	public List<ProductInfo> findUpAll();
	
	/**
	 * 查询所有并且分页
	 * @param pageable
	 * @return
	 */
	public Page<ProductInfo> findAll(Pageable pageable);
	
	public ProductInfo save(ProductInfo productInfo);
	
	
	
}
