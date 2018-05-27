package cn.walkerl.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import cn.walkerl.config.ProjectUrlConfig;
import cn.walkerl.exception.SellerAuthorizeException;

@ControllerAdvice
public class SellerExceptionHandler {

	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	
	//拦截登录异常
	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerAuthorizeException() {
		return new ModelAndView("redirect:"
				.concat(projectUrlConfig.getWechatOpenAuthorize())
				.concat("/wei-sell/wechat/qrAuthorize")
				.concat("?returnUrl=")
				.concat(projectUrlConfig.getWeiSell())
				.concat("/wei-sell/seller/login")
				);
	}
	
}
