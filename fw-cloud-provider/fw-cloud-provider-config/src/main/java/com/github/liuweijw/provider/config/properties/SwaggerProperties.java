package com.github.liuweijw.provider.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * Swagger 属性配置
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class SwaggerProperties {

	private String	title				= " Swagger API ";

	private String	description			= "https://github.com/liuweijw/fw-cloud-framework/wiki";

	private String	termsOfServiceUrl	= "https://github.com/liuweijw/fw-cloud-framework";

	private String	version				= "1.0";

	private String	license				= "Apache License 2.0";

	private String	licenseUrl			= "http://www.apache.org/licenses/LICENSE-2.0";

	private String	contactName			= "liuweijw";

	private String	contactUrl			= "https://github.com/liuweijw/fw-cloud-framework";

	private String	contactEmail		= "liuweijw.github@foxmail.com";

	private boolean	headerAuthorization	= true;

}
