package cn.walkerl.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import cn.walkerl.dataobject.ProductCategory;
import cn.walkerl.dataobject.ProductInfo;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.form.ProductForm;
import cn.walkerl.service.CategoryService;
import cn.walkerl.service.ProductService;
import cn.walkerl.utils.KeyUtil;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("CategoryServiceImpl")
	private CategoryService categoryService;
	
	/**
	 * 商品列表
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
				             @RequestParam(value = "size", defaultValue = "10") Integer size,
				             Map<String, Object> map) {
		PageRequest request = PageRequest.of(page - 1, size);
		Page<ProductInfo> productInfoPage = productService.findAll(request);
		map.put("productInfoPage", productInfoPage);
		map.put("currentPage", page);
		map.put("size", size);
		
		return new ModelAndView("product/list_product", map);
	}
	
	
	/**
	 * 商品上架
	 * @param productId
	 * @param map
	 * @return
	 */
	@RequestMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId") String productId,
							   @RequestParam(value = "page", defaultValue = "1") Integer page,
					           @RequestParam(value = "size", defaultValue = "10") Integer size,
			                   Map<String, Object> map) {
		try {
			productService.onSale(productId);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/product/list" + "?page=" + page + "&size=" + size);
			return new ModelAndView("common/error", map);
		}
		
		map.put("url", "/wei-sell/seller/product/list" + "?page=" + page + "&size=" + size);
		map.put("msg", ResultEnum.PRODUCT_ONSALE_SUCCESS.getMessge());
		return new ModelAndView("common/success", map);
		
	}
	
	
	/**
	 * 商品下架
	 * @param productId
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@RequestMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId") String productId,
								@RequestParam(value = "page", defaultValue = "1") Integer page,
					            @RequestParam(value = "size", defaultValue = "10") Integer size,
			                    Map<String, Object> map) {
		try {
			productService.offSale(productId);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/product/list" + "?page=" + page + "&size=" + size);
			return new ModelAndView("common/error", map);
		}
		
		map.put("url", "/wei-sell/seller/product/list" + "?page=" + page + "&size=" + size);
		map.put("msg", ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMessge());
		return new ModelAndView("common/success", map);
		
	}
	
	/**
	 * 新增/修改商品信息的页面
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
							  @RequestParam(value = "page", defaultValue = "1") Integer page,
				              @RequestParam(value = "size", defaultValue = "10") Integer size,
			                  Map<String, Object> map) {
		if ( !StringUtils.isEmpty(productId) ) {
			ProductInfo productInfo = productService.findById(productId);
			map.put("productInfo", productInfo);
		}
		
		//查询所有的类目
		List<ProductCategory> categoryList = categoryService.findAll();
		map.put("categoryList", categoryList);
		map.put("msg", "");
		map.put("page", page);
		map.put("size", size);
		
		return new ModelAndView("product/index_product", map);
		
	}
	
	/**
	 * 保存/更新商品信息
	 * @param form
	 * @param bindingResult
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid ProductForm form,
			                 BindingResult bindingResult,
			                 @RequestParam(value = "page", defaultValue = "1") Integer page,
					         @RequestParam(value = "size", defaultValue = "10") Integer size,
			                 Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/wei-sell/seller/product/index");
			return new ModelAndView("common/error", map);
		}
		
		ProductInfo productInfo = new ProductInfo();
		try {
			//如果productId不为空，则从数据库查找该商品
			if ( !StringUtils.isEmpty(form.getProductId()) ) {
				productInfo = productService.findById(form.getProductId());
			} else {
				//如果productId为空，说明是新增，调用方法创建商品ID
				form.setProductId(KeyUtil.genUniqueKey4());
			}
			BeanUtils.copyProperties(form, productInfo);
			productService.save(productInfo);
			
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/product/index");
			return new ModelAndView("common/error", map);
		}
		
		map.put("msg", ResultEnum.PRODUCTINFO_SAVE_SUCCESS.getMessge());
		map.put("url", "/wei-sell/seller/product/list" + "?page=" + page + "&size=" + size);
		return new ModelAndView("common/success", map);
		
		
		
	}
	
	
	
	
}
