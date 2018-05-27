package cn.walkerl.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;




@Component
public class WechatMpConfig {

	@Autowired
	private WechatAccountConfig accountConfig;
	
	
	// 将Id和密钥传给WxMpServiceBaseImpl
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		
		return wxMpService;
	}
	
	
	//获取配置文件中记录的Id和密钥
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
		wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
		
		return wxMpConfigStorage;
	}
	
	
}
