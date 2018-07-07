package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * Redis cache config set
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class RedisConfigProperties {

	/**
	 * 缓存前缀,默认'_'
	 */
	private String	prefix		= "_";

	/**
	 * 自定义 redis expiration 单位秒<br/>
	 * 默认5分钟
	 */
	private int		expiration	= 300;

}
