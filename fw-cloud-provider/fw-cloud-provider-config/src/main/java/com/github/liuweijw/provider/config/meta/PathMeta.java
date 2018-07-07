package com.github.liuweijw.provider.config.meta;

import java.io.Serializable;

import lombok.Data;

/**
 * PathMeta
 *
 * @author liuweijw
 */
@Data
public class PathMeta implements Serializable {

	private static final long	serialVersionUID	= 1498404943076628877L;

	/**
	 * 应用名称
	 */
	private String				owner;

	/**
	 * 文件路径
	 */
	private String				path;

	/**
	 * 描述
	 */
	private String				description;

}
