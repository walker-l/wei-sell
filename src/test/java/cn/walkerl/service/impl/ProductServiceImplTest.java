package cn.walkerl.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dataobject.ProductInfo;
import cn.walkerl.enums.ProductStatusEnum;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	private ProductServiceImpl productService;
	
	@Test
	public void testFindById() throws Exception{
		ProductInfo productInfo = productService.findById("123456");
		TestCase.assertEquals("123456", productInfo.getProductId());
	}

	@Test
	
	public void testFindUpAll() throws Exception{
		List<ProductInfo> productInfoList = productService.findUpAll();
		TestCase.assertNotSame(new Integer(0), productInfoList.size());
	}

	@Test
	
	public void testFindAll() throws Exception{
		PageRequest request = new PageRequest(0, 2);
		Page<ProductInfo> productInfoPage = productService.findAll(request);
		TestCase.assertNotSame(new Integer(0), productInfoPage.getTotalElements());
	}

	@Test
	
	public void testSave() throws Exception{
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123457");
		productInfo.setProductName("皮皮虾");
		productInfo.setProductPrice(new BigDecimal(7.5));
		productInfo.setProductStock(100);
		productInfo.setProductDescription("很好吃的虾");
		productInfo.setProductIcon("http://xxxx.jpg");
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		productInfo.setCategoryType(2);
		
		ProductInfo result = productService.save(productInfo);
		TestCase.assertNotNull(result);
	}

}
