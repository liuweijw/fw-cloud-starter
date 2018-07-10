package com.github.liuweijw.provider.core.beans;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

import lombok.Data;

/**
 * 文件状态更新通知消息
 *
 * @author liuweijw
 */
@Data
@ApiModel(description = "文件状态更新通知")
public class FileMsgModel implements Serializable {

	private static final long	serialVersionUID	= -1021719282499966806L;

	/**
	 * 文件ID
	 */
	private String[]			attIdArray;

	/**
	 * 更新状态
	 */
	private Integer				statu;

	/**
	 * 从数据库、文件目录删除
	 */
	private boolean				isDel				= false;
}
