package cn.walkerl.repository;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.walkerl.dataobject.SellerInfo;
import cn.walkerl.utils.KeyUtil;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

	@Autowired
	private SellerInfoRepository repository;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	@Transactional
	public void testSave() {
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setSellerId(KeyUtil.genUniqueKey());
		sellerInfo.setUsername("admin");
		sellerInfo.setPassword("admin");
		sellerInfo.setOpenid("abc");
		
		SellerInfo resultInfo = repository.save(sellerInfo);
		
		TestCase.assertNotNull(resultInfo);
	}
	
	
	@Test
	@Transactional
	public void testFindByOpenid() throws Exception{
		SellerInfo result = repository.findByOpenid("abc");
		
		TestCase.assertEquals("abc", result.getOpenid());
	}

	

}
