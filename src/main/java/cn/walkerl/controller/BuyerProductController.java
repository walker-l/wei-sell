package cn.walkerl.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.walkerl.VO.ProductInfoVO;
import cn.walkerl.VO.ProductVO;
import cn.walkerl.VO.ResultVO;
import cn.walkerl.dataobject.ProductCategory;
import cn.walkerl.dataobject.ProductInfo;
import cn.walkerl.service.CategoryService;
import cn.walkerl.service.ProductService;
import cn.walkerl.utils.ResultVOUtil;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list")
	public ResultVO  list() {
		//1. 查询所有的上架商品
		List<ProductInfo> productInfoList = productService.findUpAll();
		
		//2.查询类目(一次性查询)
		//精简方法(java8,lambda)
		List<Integer> categoryTypeList = productInfoList.stream()
				.map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList); 
		
		//3. 数据拼装
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategory productCategory: productCategoryList)  {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryType(productCategory.getCategoryType());
			productVO.setCategoryName(productCategory.getCategoryName());
			
			List<ProductInfoVO> productInfoVoList = new ArrayList<>();
			for (ProductInfo productInfo: productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVoList.add(productInfoVO);
				}
			}
			productVO.setProductInfoVOList(productInfoVoList);
			productVOList.add(productVO);
		}
		
		return ResultVOUtil.success(productVOList);
	}
}
