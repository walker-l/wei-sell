package cn.walkerl.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dataobject.ProductInfo;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

	@Autowired
	private ProductInfoRepository repository;
	
	@Test
	public void testFindByProductStatus() throws Exception{
		List<ProductInfo> productInfoList = repository.findByProductStatus(0);
		TestCase.assertNotNull(productInfoList.size());
	}

	@Test
	@Transactional
	public void testSave() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123456");
		productInfo.setProductName("皮蛋粥");
		productInfo.setProductPrice(new BigDecimal(5.0));
		productInfo.setProductStock(100);
		productInfo.setProductDescription("很好吃的粥");
		productInfo.setProductIcon("http://xxxx.jpg");
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(2);
		
		ProductInfo result = repository.save(productInfo);
		TestCase.assertNotNull(result);
		
	}

}
