package com.github.liuweijw.provider.config.properties;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.github.liuweijw.provider.config.meta.PathMeta;

/**
 * file config set
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class FileProperties {

	/**
	 * 根路径
	 */
	private String					rootPath;

	/**
	 * 文件路径 ( key : pathCode )
	 */
	private Map<String, PathMeta>	path;

	/**
	 * 编码
	 */
	private String					encoding	= "UTF-8";

}
