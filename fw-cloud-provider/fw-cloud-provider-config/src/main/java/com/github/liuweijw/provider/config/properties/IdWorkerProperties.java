package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 分布式Id相关配置
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class IdWorkerProperties {

	/**
	 * 最大个数4096
	 */
	private int		maxCount	= 4096;

	/**
	 * 0 - 31 的数字
	 */
	private Integer	centerId;

	/**
	 * 0 - 31 的数字
	 */
	private Integer	workerId;

	/**
	 * 缓存前缀,默认'_'
	 */
	private String	prefix		= "_";

}
