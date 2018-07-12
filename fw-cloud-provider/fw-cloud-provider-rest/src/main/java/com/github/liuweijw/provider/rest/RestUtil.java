package com.github.liuweijw.provider.rest;

import org.springframework.web.client.RestTemplate;

import com.github.liuweijw.commons.utils.StringHelper;

/**
 * 通过请求URL获取不同类别的RestTemplate
 * 
 * @author liuweijw
 */
public class RestUtil {

	public static final String	HTTPS	= "https";

	public static RestTemplate restTemplate(String reqUrl) {
		if (StringHelper.isBlank(reqUrl)) return new RestTemplate();

		return reqUrl.startsWith(HTTPS) ? new RestTemplate(new RestClientRequestFactory())
				: new RestTemplate();
	}
}
