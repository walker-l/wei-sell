package cn.walkerl.service.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dataobject.OrderDetail;
import cn.walkerl.dto.OrderDTO;
import cn.walkerl.enums.OrderStatusEnum;
import cn.walkerl.enums.PayStatusEnum;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

	@Autowired
	@Qualifier("OrderServiceImpl")
	private OrderServiceImpl orderService;
	
	private final String BUYER_OPENID = "1101120";
			
	private final String ORDER_ID = "1526039461401487534";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Transactional
	public void testCreate() throws Exception{
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName("金角大王");
		orderDTO.setBuyerAddress("平顶山");
		orderDTO.setBuyerPhone("15867926482");
		orderDTO.setBuyerOpenid(BUYER_OPENID);
		
		//购物车
		List<OrderDetail> orderDetailList = new ArrayList<>();
		OrderDetail o1 = new OrderDetail();
		o1.setProductId("1235");
		o1.setProductQuantity(2);
		
		OrderDetail o2 = new OrderDetail();
		o2.setProductId("1236");
		o2.setProductQuantity(3);
		
		orderDetailList.add(o1);
		orderDetailList.add(o2);
		
		orderDTO.setOrderDetailList(orderDetailList);
		
		OrderDTO result = orderService.create(orderDTO);
		log.info("【创建订单】result={}", result);
		TestCase.assertNotNull(result);
	}

	@Test
	@Transactional
	//@Ignore
	public void testFindOne() throws Exception{
		OrderDTO result = orderService.findOne(ORDER_ID);
		log.info("【查询单个订单】result= {}", result);;
		TestCase.assertEquals(ORDER_ID, result.getOrderId());
	}

	@Test
	@Transactional
	//@Ignore
	public void testFindListStringPageable() throws Exception{
		PageRequest request = PageRequest.of(0, 2);
		Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
		TestCase.assertNotNull(orderDTOPage.getTotalElements());
	}

	@Test
	@Transactional
	//@Ignore
	public void testCancel() throws Exception{
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.cancel(orderDTO);
		TestCase.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
	}

	@Test
	@Transactional
	//@Ignore
	public void testFinish() throws Exception {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.finish(orderDTO);
		TestCase.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	}

	@Test
	@Transactional
	//@Ignore
	public void testPaid() throws Exception{
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.paid(orderDTO);
		TestCase.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
	}

	@Test
	@Transactional
	//@Ignore
	public void testFindListPageable() throws Exception {
		PageRequest request = PageRequest.of(1, 2);
		Page<OrderDTO> orderDTOPage = orderService.findList(request);
	    TestCase.assertNotNull(orderDTOPage.getTotalElements());
	}

}
