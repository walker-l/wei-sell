package cn.walkerl.repository;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dataobject.OrderMaster;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

	@Autowired
    private OrderMasterRepository repository;
	
	private final String OPENID = "110110";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByBuyerOpenid() {
		PageRequest request = PageRequest.of(0, 5);
		
		Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);
		
		TestCase.assertNotNull(result.getTotalElements());
	}

	@Test
	public void testSave() {
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId("1234567");
		orderMaster.setBuyerName("二师兄");
		orderMaster.setBuyerPhone("1234567890");
		orderMaster.setBuyerAddress("高老庄");
		orderMaster.setBuyerOpenid(OPENID);
		orderMaster.setOrderAmount(new BigDecimal(5.8));
		
		OrderMaster result = repository.save(orderMaster);
		TestCase.assertNotNull(result);
	}

}
