package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * GlobalConstant.ROOT_PREFIX 开头的相关配置
 * 
 * @author liuweijw
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "cloud")
public class ConfigProperties {

	/**
	 * swagger 配置属性
	 */
	private SwaggerProperties		swagger			= new SwaggerProperties();

	/**
	 * rest 配置属性
	 */
	private RestTemplateProperties	restTemplate	= new RestTemplateProperties();

	/**
	 * wechat 微信相关配置属性
	 */
	private WechatProperties		wechat			= new WechatProperties();

	/**
	 * wechatPay 微信支付相关配置属性
	 */
	private WechatPayProperties		wechatPay		= new WechatPayProperties();

	/**
	 * redis 相关配置属性
	 */
	private RedisConfigProperties	redis			= new RedisConfigProperties();

	/**
	 * idWorker相关配置属性
	 */
	private IdWorkerProperties		idWorker		= new IdWorkerProperties();

	/**
	 * file相关配置属性
	 */
	private FileProperties			file			= new FileProperties();
}
