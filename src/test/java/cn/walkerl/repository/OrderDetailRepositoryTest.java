package cn.walkerl.repository;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dataobject.OrderDetail;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository repository;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByOrderId() {
		List<OrderDetail> orderDetailList = repository.findByOrderId("1234567");
		TestCase.assertNotNull(orderDetailList.size());
	}

	@Test
	public void testSave() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setDetailId("1234567810");
		orderDetail.setOrderId("1234567");
		orderDetail.setProductIcon("http://xxx.jpg");
		orderDetail.setProductId("11111112");
		orderDetail.setProductName("皮蛋粥");
		orderDetail.setProductPrice(new BigDecimal(2.2));
		orderDetail.setProductQuantity(3);
		
		OrderDetail result = repository.save(orderDetail);
		TestCase.assertNotNull(result);
	}

}
