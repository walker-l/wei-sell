package cn.walkerl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
public class WechatOpenConfig {

	@Autowired
	private WechatAccountConfig accountConfig;
	
	// 将Id和密钥传给WxMpServiceBaseImpl
	public WxMpService wxOpenService() {
		WxMpService wxOpenService = new WxMpServiceImpl();
		wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
		return wxOpenService;
	}
	
	
	//获取配置文件中记录的Id和密钥
	public WxMpConfigStorage wxOpenConfigStorage() {
		WxMpInMemoryConfigStorage wxOpenInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxOpenInMemoryConfigStorage.setAppId(accountConfig.getOpenAppId());
		wxOpenInMemoryConfigStorage.setSecret(accountConfig.getOpenAppSecret());
		return wxOpenConfigStorage();
	}
	
}
