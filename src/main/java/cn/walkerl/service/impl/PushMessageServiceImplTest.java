package cn.walkerl.service.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dto.OrderDTO;
import cn.walkerl.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

	@Autowired
	private PushMessageServiceImpl pushMessageService;
	
	@Autowired
	private OrderService orderService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrderStatus() throws Exception{
		OrderDTO orderDTO = orderService.findOne("1526424665184416035");
		pushMessageService.orderStatus(orderDTO);
	}

}
