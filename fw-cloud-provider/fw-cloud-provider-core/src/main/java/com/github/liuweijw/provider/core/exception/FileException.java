package com.github.liuweijw.provider.core.exception;

/**
 * 文件异常信息
 * 
 * @author liuweijw
 */
public class FileException extends RuntimeException {

	private static final long	serialVersionUID	= -7185277089295127324L;

	public FileException(String message) {
		super(message);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

}
