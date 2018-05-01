package cn.walkerl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.walkerl.dataobject.ProductInfo;
import cn.walkerl.enums.ProductStatusEnum;
import cn.walkerl.repository.ProductInfoRepository;
import cn.walkerl.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductInfoRepository productInfoRepository;
	
	@Override
	public  ProductInfo findById(String productId) {
		
		return productInfoRepository.findByProductId(productId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		
		return productInfoRepository.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		
		return productInfoRepository.save(productInfo);
	}

}
