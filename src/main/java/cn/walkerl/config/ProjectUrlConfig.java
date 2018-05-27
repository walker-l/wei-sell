package cn.walkerl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
@PropertySource(value = "classpath:application.yml")
public class ProjectUrlConfig {

	/**
	 * 微信公众平台授权url
	 */
	public String wechatMpAuthorize;
	
	/**
	 * 微信开放平台授权url
	 */
	public String wechatOpenAuthorize;
	
	/**
	 * 点餐系统
	 */
	public String weiSell;
	
}
