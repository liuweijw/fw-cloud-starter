package com.github.liuweijw.provider.id.beans;

import lombok.Data;

import com.github.liuweijw.provider.id.IdWorker;

/**
 * IdWorker 封装
 * 
 * @author liuweijw
 */
@Data
public class IdWorkerWrapper {

	/**
	 * 数据中心ID ( 0 - 31 )
	 */
	private long			centerId;

	/**
	 * 机器ID ( 0 - 31 )
	 */
	private long			workerId;

	/**
	 * ID生成器
	 */
	private IdWorker		idWorker;

	/**
	 * 缓存键
	 */
	private String			cacheKey;

	/**
	 * host ip 缓存值
	 */
	private ServiceCache	serviceCache;

}
