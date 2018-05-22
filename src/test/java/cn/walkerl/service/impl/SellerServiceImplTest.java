package cn.walkerl.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.walkerl.dataobject.SellerInfo;
import junit.framework.TestCase;

public class SellerServiceImplTest {

	
	private static final String openid = "abc";
	
	@Autowired
	@Qualifier("SellerServiceImpl")
	private SellerServiceImpl sellerService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindSellerInfoByOpenid() throws Exception {
		SellerInfo result = sellerService.findSellerInfoByOpenid(openid);
		
		TestCase.assertEquals(openid, result.getOpenid());
	}

}
