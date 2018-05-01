package cn.walkerl.repository;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.walkerl.dataobject.ProductCategory;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

	@Autowired
	private ProductCategoryRepository repository;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByCategoryTypeIn() {
		List<Integer> list = Arrays.asList(2,3,4);
		
		List<ProductCategory> result = repository.findByCategoryTypeIn(list);
		TestCase.assertNotNull(result.size());
	}

	@Test
	@Transactional
	public void testSave() {
		ProductCategory productCategory = new ProductCategory("男生最爱",4);
		ProductCategory result = repository.save(productCategory);
		TestCase.assertNotNull(result);
	}

	@Test
	public void testFindByCategoryId() {
		ProductCategory productCategory = repository.findByCategoryId(1);
		System.out.println(productCategory.toString());
	}

}
