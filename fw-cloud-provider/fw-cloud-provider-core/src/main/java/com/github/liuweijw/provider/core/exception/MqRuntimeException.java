package com.github.liuweijw.provider.core.exception;

/**
 * MQ 服务异常处理
 * 
 * @author liuweijw
 */
public class MqRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -631971868622312213L;

	public MqRuntimeException(String message) {
		super(message);
	}

	public MqRuntimeException(Throwable cause) {
		super(cause);
	}

	public MqRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
