package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

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
	 * 描述 : 缓存前缀,默认'_'
	 */
	private String	prefix		= "_";

}
