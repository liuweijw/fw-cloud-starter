package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信支付相关配置
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class WechatPayProperties {

	/**
	 * 和支付服务指定的mch_id 一致
	 */
	private String	mch_id;

	/**
	 * 和支付服务指定请求私钥相同，网络传输中加密
	 */
	private String	req_key;

	/**
	 * 和支付服务指定相应私钥相同，获取相应数据之后解密用
	 */
	private String	res_key;

	/**
	 * 公众号支付回调应用URL
	 */
	private String	notify_url;

}
