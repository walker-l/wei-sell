package cn.walkerl.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
@PropertySource(value = "classpath:application.yml")
public class WechatAccountConfig {

	/**
	 * 公众平台id
	 */
	private String mpAppId;
	
	/**
	 * 公众平台密钥
	 */
	private String mpAppSecret;
	
	/**
	 * 开放平台id
	 */
	private String openAppId;
	
	/**
	 * 开放平台密钥
	 */
	private String openAppSecret;
	
	/**
	 * 微信模板消息id
	 */
	private Map<String, String> templateId;
	
}
