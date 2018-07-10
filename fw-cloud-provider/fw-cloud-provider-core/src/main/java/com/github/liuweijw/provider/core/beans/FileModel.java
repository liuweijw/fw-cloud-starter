package com.github.liuweijw.provider.core.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * 文件信息
 *
 * @author liuweijw
 */
@Data
@ApiModel(description = "文件信息")
public class FileModel implements Serializable {

	private static final long	serialVersionUID	= 2250174431104437745L;

	/**
	 * 文件attId
	 */
	@ApiModelProperty(value = "文件attId", required = true, dataType = "string")
	private String				attId;

	/**
	 * 文件编码
	 */
	@ApiModelProperty(value = "文件编码", required = true, dataType = "string")
	private String				attCode;

	/**
	 * 上传时文件名称
	 */
	@ApiModelProperty(value = "上传时文件名称", required = true, dataType = "string")
	private String				originalName;

	/**
	 * 上传之后重命名
	 */
	@ApiModelProperty(value = "上传之后重命名", required = true, dataType = "string")
	private String				reName;

	/**
	 * 文件类型编码
	 */
	@ApiModelProperty(value = "文件类型编码", required = true, dataType = "string")
	private String				pathCode;

	/**
	 * 文件pathType
	 */
	@ApiModelProperty(value = "模块编码", required = true, dataType = "string")
	private String				pathType;

	/**
	 * 文件相对路径
	 */
	@ApiModelProperty(value = "文件相对路径", required = true, dataType = "string")
	private String				relativePath;

	/**
	 * 文件扩展名
	 */
	@ApiModelProperty(value = "文件扩展名", required = true, dataType = "string")
	private String				suffix;

	/**
	 * 上下文类型
	 */
	@ApiModelProperty(value = "上下文类型", required = true, dataType = "string")
	private String				contentType;

	/**
	 * 文件长度
	 */
	@ApiModelProperty(value = "文件长度", required = true, dataType = "long")
	private Long				size;

	/**
	 * 文件状态
	 */
	@ApiModelProperty(value = "文件状态", required = true, dataType = "int")
	private Integer				statu				= 0;
}
