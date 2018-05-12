package cn.walkerl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.walkerl.config.WechatMpConfig;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import cn.walkerl.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	
	
	
	@Autowired
	private WechatMpConfig weMpConfig;
	
	@GetMapping("/authorize")
	public String authorize(
			@RequestParam("returnUrl") String returnUrl) {
		//1.配置
		//2.调用方法
		String url = "http://walkerl.cn/wei-sell/wechat/userInfo";
		
		//调用KeyUtil生成随机字符串作为state参数，用于校验接收的响应是否是对应自己的请求的
		//String state = KeyUtil.genUniqueKey();
		
		WxMpService wxmp = weMpConfig.wxMpService();
		String redirectUrl = wxmp.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;
	}
	
	@GetMapping("/userInfo")
	public String userInfo(
			@RequestParam("code") String code,
			@RequestParam("state") String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		WxMpService wxmp = weMpConfig.wxMpService();
		
		try {
			wxMpOAuth2AccessToken = wxmp.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】{}", e);
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
			
		}
		
		String openId = wxMpOAuth2AccessToken.getOpenId();
		
		return "redirect:" + returnUrl + "?openid=" + openId;
	}
}
