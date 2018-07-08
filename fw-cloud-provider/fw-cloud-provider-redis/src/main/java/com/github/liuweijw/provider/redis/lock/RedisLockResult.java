package com.github.liuweijw.provider.redis.lock;

import lombok.Data;
import lombok.ToString;

/**
 * redis lock
 * 
 * @author liuweijw
 */
@Data
@ToString
public class RedisLockResult {

	/**
	 * 是否加锁
	 */
	private Boolean	lock	= false;

	/**
	 * 返回值
	 */
	private String	value;

}
