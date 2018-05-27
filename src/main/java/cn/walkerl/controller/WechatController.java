package cn.walkerl.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.walkerl.config.ProjectUrlConfig;
import cn.walkerl.config.WechatMpConfig;
import cn.walkerl.config.WechatOpenConfig;
import cn.walkerl.enums.ResultEnum;
import cn.walkerl.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	
	
	
	@Autowired
	private WechatMpConfig weMpConfig;
	
	@Autowired
	private WechatOpenConfig wxOpenConfig;
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	
	/**
	 * 构建手机微信公众号授权url
	 * @param returnUrl
	 * @return
	 */
	@GetMapping("/authorize")
	public String authorize(
			@RequestParam("returnUrl") String returnUrl) {
		//1.配置
		//2.调用方法
		String url = projectUrlConfig.getWechatMpAuthorize() + "/wei-sell/wechat/userInfo";
		
		//调用KeyUtil生成随机字符串作为state参数，用于校验接收的响应是否是对应自己的请求的
		//String state = KeyUtil.genUniqueKey();
		
		WxMpService wxMp = weMpConfig.wxMpService();
		String redirectUrl = wxMp.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl + "?returnUrl=" + returnUrl;
	}
	
	
	/**
	 * 授权成功后重定向的接口，通过code换取AccessToken，成功后会同时获得openid
	 * @param code
	 * @param returnUrl
	 * @return
	 */
	@GetMapping("/userInfo")
	public String userInfo(
			@RequestParam("code") String code,
			@RequestParam("returnUrl") String returnUrl,
			@RequestParam("state") String state) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		WxMpService wxMp = weMpConfig.wxMpService();
		
		try {
			wxMpOAuth2AccessToken = wxMp.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信公众号授权】{}", e);
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
			
		}
		
		String openId = wxMpOAuth2AccessToken.getOpenId();
		
		return "redirect:" + returnUrl + "?openid=" + openId;
	}
	
	
	
	/**
	 * 构建微信网页应用授权url
	 * @param returnUrl
	 * @return
	 */
	@GetMapping("/qrAuthorize")
	public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
		String url = projectUrlConfig.getWechatOpenAuthorize() + "/wei-sell/wechat/qrUserInfo";
		WxMpService wxOpen = wxOpenConfig.wxOpenService();
		String redirectUrl = wxOpen.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl + "?returnUrl" + returnUrl;
	}
	
	
	/**
	 * 授权成功后重定向的接口，通过code换取AccessToken，成功后会同时获得openid
	 * @param code
	 * @param returnUrl
	 * @param state
	 * @return
	 */
	@GetMapping("/qrUserInfo")
	public String qrUserInfo(@RequestParam("code") String code,
			                 @RequestParam("returnUrl") String returnUrl,
			                 @RequestParam("state") String state) {
		WxMpOAuth2AccessToken wxOpenOAuth2AccessToken = new WxMpOAuth2AccessToken();
		WxMpService wxOpen = wxOpenConfig.wxOpenService();
		
		try {
			wxOpenOAuth2AccessToken = wxOpen.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】{}", e);
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
		}
		
		log.info("wxOpenOAuth2AccessToken={}", wxOpenOAuth2AccessToken);
		String openId = wxOpenOAuth2AccessToken.getOpenId();
		
		return "redirect:" + returnUrl + "?openid=" + openId; 
	}
	
	
	
}
