package cn.walkerl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
@PropertySource(value = "classpath:application.yml")
public class WechatAccountConfig {

	
	private String mpAppId;
	
	private String mpAppSecret;
	
	
	
}
