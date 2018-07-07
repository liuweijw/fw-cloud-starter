package com.github.liuweijw.provider.id.beans;

import java.io.Serializable;

import lombok.Data;

/**
 * 服务缓存
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
