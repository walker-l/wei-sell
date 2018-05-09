package cn.walkerl.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;

import javax.security.sasl.SaslException;
import javax.transaction.Transactional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.walkerl.dataobject.ProductInfo;
import cn.walkerl.dto.CartDTO;
import cn.walkerl.enums.ProductStatusEnum;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.repository.ProductInfoRepository;
import cn.walkerl.service.ProductService;

@Service("ProductServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductInfoRepository repository;
	
	@Override
	public  ProductInfo findById(String productId) {
		
		return repository.findByProductId(productId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		
		return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		
		return repository.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		
		return repository.save(productInfo);
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO: cartDTOList) {
			ProductInfo productInfo = repository.findByProductId(cartDTO.getProductId());
			
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			
			Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
			productInfo.setProductStock(result);
			
			repository.save(productInfo);
		}
		
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			ProductInfo productInfo = repository.findByProductId(cartDTO.getProductId());
			
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			
			Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
			
			if (result < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			
			productInfo.setProductStock(result);
			
			repository.save(productInfo);
		}
		
	}

	
}
