package cn.walkerl.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.walkerl.dataobject.ProductCategory;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.form.CategoryForm;
import cn.walkerl.service.CategoryService;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	@Qualifier("CategoryServiceImpl")
	private CategoryService categoryService;
	
	
	/**
	 * 类目列表
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
		Page<ProductCategory> categoryPage = categoryService.findAll(request);
		map.put("categoryPage", categoryPage);
		map.put("currentPage", page);
        map.put("size", size);
        
        return new ModelAndView("category/list_category", map);
	}
	
	
	/**
	 * 新增/修改类目的页面
	 * @param categoryId
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
							  @RequestParam(value = "page", defaultValue = "1") Integer page,
				              @RequestParam(value = "size", defaultValue = "10") Integer size,
				              Map<String, Object> map) {
		if (categoryId != null) {
			ProductCategory productCategory = categoryService.findOne(categoryId);
			map.put("category", productCategory);
		}
		
		map.put("msg", "");
		map.put("page", page);
		map.put("size", size);
		
		return new ModelAndView("category/index_category", map);
	}
	
	
	/**
	 * 保存/更新类目信息
	 * @param form
	 * @param bindingResult
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@PostMapping("/save")
	@CacheEvict(cacheNames = "product", key = "productList")
	public ModelAndView save(@Valid CategoryForm form,
				            BindingResult bindingResult,
				            @RequestParam(value = "page", defaultValue = "1") Integer page,
					        @RequestParam(value = "size", defaultValue = "10") Integer size,
				            Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/wei-sell/seller/category/index");
			return new ModelAndView("common/error",map);
		}
		
		ProductCategory productCategory = new ProductCategory();
		try {
			if (form.getCategoryId() != null) {
				productCategory = categoryService.findOne(form.getCategoryId());
			}
			BeanUtils.copyProperties(form, productCategory);
			categoryService.save(productCategory);
			
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/wei-sell/seller/category/index");
			return new ModelAndView("common/error", map);
		}
		
		map.put("msg", ResultEnum.CATEGORY_SAVE_SUCCESS.getMessge());
		map.put("url", "/wei-sell/seller/category/list" + "?page=" + page + "&size=" + size);
		
		return new ModelAndView("common/success", map);
		
	}
	
	
	
}
