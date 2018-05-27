package cn.walkerl.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.walkerl.config.ProjectUrlConfig;
import cn.walkerl.constant.CookieConstant;
import cn.walkerl.constant.RedisConstant;
import cn.walkerl.dataobject.SellerInfo;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.service.SellerService;
import cn.walkerl.utils.CookieUtil;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	
	@GetMapping("/login")
	public ModelAndView login(@RequestParam("openid") String openid,
			                  HttpServletResponse response,
			                  Map<String, Object> map) {
		//1. 将传入的openid和数据库中的数据匹配
		SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
		if (sellerInfo == null) {
			map.put("mag", ResultEnum.LOGIN_FAIL.getMessge());
			map.put("url", "/wei-sell/seller/order/list");
			return new ModelAndView("common/error");
		}
		
		
		//2. 设置token至redis
		String token = UUID.randomUUID().toString();
		Integer expire = RedisConstant.EXPIRE;
		
		redisTemplate.opsForValue().set(
				String.format(RedisConstant.TOKEN_PREFIX, token), 
				openid, expire, TimeUnit.SECONDS);
		
		
		//3. 设置token至cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
		
		return new ModelAndView("redirect:" + projectUrlConfig.getWeiSell() + "/wei-sell/seller/order/list");
		
		
		
	}
	
	
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
			                   HttpServletResponse response,
			                   Map<String, Object> map) {
		//1. 从cookie里查询
	Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
	if (cookie != null) {
		//2. 清除redis
		redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		
		//3. 清除cookie
		CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
	}
	
	map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessge());
	map.put("url", "/wei-sell/seller/order/list");
	
	return new ModelAndView("common/success", map);
		
	}
	
	
	
	
	
	
	
}
