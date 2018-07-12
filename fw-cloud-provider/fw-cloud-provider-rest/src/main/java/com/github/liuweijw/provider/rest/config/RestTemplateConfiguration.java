package com.github.liuweijw.provider.rest.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.github.liuweijw.provider.config.properties.ConfigProperties;

/**
 * RestTemple Config
 * 
 * @author liuweijw
 */
@Configuration
public class RestTemplateConfiguration {

	@Resource
	private ConfigProperties	fwConfigProperties;

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(fwConfigProperties.getRestTemplate().getReadTimeout());
		factory.setConnectTimeout(fwConfigProperties.getRestTemplate().getConnectTimeout());
		return factory;
	}
}
