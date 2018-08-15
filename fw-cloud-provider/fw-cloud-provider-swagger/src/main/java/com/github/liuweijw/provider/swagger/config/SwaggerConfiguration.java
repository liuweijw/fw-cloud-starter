package com.github.liuweijw.provider.swagger.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.liuweijw.provider.config.properties.ConfigProperties;
import com.github.liuweijw.provider.config.properties.SwaggerProperties;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API 文档配置
 * 
 * @author liuweijw
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Resource
	private ConfigProperties fwConfigProperties;

	@Bean
	public Docket createRestApi() {
		List<Parameter> operationParameters = new ArrayList<Parameter>();
		SwaggerProperties swaggerProperties = fwConfigProperties.getSwagger();
		if (swaggerProperties.isHeaderAuthorization()) {
			ParameterBuilder parameterBuilder = new ParameterBuilder();
			parameterBuilder.name("Authorization")
					.defaultValue("Bearer token")
					.description("Bearer 请求中获取heard中token参数|获取cookie中的x-access-token值")
					.modelRef(new ModelRef("string"))
					.parameterType("header")
					.required(true);
			operationParameters.add(parameterBuilder.build());
		}
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(operationParameters);
	}

	private ApiInfo apiInfo() {
		SwaggerProperties swaggerProperties = fwConfigProperties.getSwagger();
		return new ApiInfoBuilder().title(swaggerProperties.getTitle())
				.description(swaggerProperties.getDescription())
				.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
				.contact(
						new Contact(
								swaggerProperties.getContactName(), swaggerProperties.getContactUrl(),
								swaggerProperties.getContactEmail()))
				.version(swaggerProperties.getVersion())
				.build();
	}
}
