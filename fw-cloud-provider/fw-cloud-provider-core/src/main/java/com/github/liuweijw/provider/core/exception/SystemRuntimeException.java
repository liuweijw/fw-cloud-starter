package com.github.liuweijw.provider.core.exception;

/**
 * 系统异常处理
 * 
 * @author liuweijw
 */
public class SystemRuntimeException extends RuntimeException {

	private static final long	serialVersionUID	= -8601534347656439209L;

	public SystemRuntimeException(String message) {
		super(message);
	}

	public SystemRuntimeException(Throwable cause) {
		super(cause);
	}

	public SystemRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
