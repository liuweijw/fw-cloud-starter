package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * RestTemplate config set
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class RestTemplateProperties {

	/**
	 * http read timeout ms
	 */
	private int	readTimeout		= 15000;

	/**
	 * http connect timeout ms
	 */
	private int	connectTimeout	= 15000;
}
