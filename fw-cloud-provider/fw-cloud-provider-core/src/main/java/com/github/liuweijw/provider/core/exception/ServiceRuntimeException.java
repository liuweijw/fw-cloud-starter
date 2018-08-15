package com.github.liuweijw.provider.core.exception;

/**
 * 全局服务异常处理
 * 
 * @author liuweijw
 */
public class ServiceRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7643334187736726895L;

	public ServiceRuntimeException(String message) {
		super(message);
	}

	public ServiceRuntimeException(Throwable cause) {
		super(cause);
	}

	public ServiceRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
