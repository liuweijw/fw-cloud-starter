package com.github.liuweijw.provider.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.liuweijw.provider.config.properties.ConfigProperties;

/**
 * 项目配置入口，统一规范
 * 
 * @author liuweijw
 */
@Configuration
@EnableConfigurationProperties(value = ConfigProperties.class)
public class FwCloudConfig {

}
