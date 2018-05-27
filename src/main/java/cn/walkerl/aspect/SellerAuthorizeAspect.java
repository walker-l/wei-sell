package cn.walkerl.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.walkerl.constant.CookieConstant;
import cn.walkerl.constant.RedisConstant;
import cn.walkerl.exception.SellerAuthorizeException;
import cn.walkerl.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 监听以Seller开头的controller的所有方法，排除SellerUserController的所有方法
	 */
	@Pointcut("execution(public * cn.walkerl.controller.Seller*.*(..))" 
	+ "&& !execution(public * cn.walkerl.controller.SellerUserController.*(..))")
	public void verify() {
		
	}
	
	@Before("verify()")
	public void doVerify() {
		//获取HttpRequest
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		//查询cookie
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie == null) {
			log.warn("【登录校验】Cookie中查不到token");
			throw new SellerAuthorizeException();
		}
		
		//去redis里查询
		String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		if (StringUtils.isEmpty(tokenValue)) {
			log.warn("【登录校验】Redis中查不到token");
			throw new  SellerAuthorizeException();
		}
		
	}
}
