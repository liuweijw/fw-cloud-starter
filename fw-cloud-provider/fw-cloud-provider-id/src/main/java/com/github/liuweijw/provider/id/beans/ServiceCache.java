package com.github.liuweijw.provider.id.beans;

import java.io.Serializable;

import lombok.Data;

/**
 * 服务缓存，可以扩展为服务单独部署或者请求远程接口
 * 
 * @author liuweijw
 */
@Data
public class ServiceCache implements Serializable {

	private static final long	serialVersionUID	= 2665288390514264548L;

	/**
	 * host
	 */
	private String				host;

	/**
	 * 端口号
	 */
	private int					port;

}
