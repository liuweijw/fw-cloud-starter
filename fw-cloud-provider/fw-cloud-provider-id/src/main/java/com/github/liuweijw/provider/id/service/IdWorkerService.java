package com.github.liuweijw.provider.id.service;

import java.util.List;

/**
 * 分布式id 缓存相关
 * 
 * @author liuweijw
 */
public interface IdWorkerService {

	/**
	 * 获得分布式ID
	 *
	 * @return 分布式ID
	 */
	public String nextId();

	/**
	 * 获得分布式ID
	 *
	 * @param dataWorkId
	 *            数据中心ID | 机器ID ( 0 - 1023 )
	 * @return 分布式ID
	 */
	public String nextId(Integer dataWorkId);

	/**
	 * 获得分布式ID
	 *
	 * @param centerId
	 *            数据中心ID ( 0 - 31 )
	 * @param workerId
	 *            机器ID ( 0 - 31 )
	 * @return 分布式ID
	 */
	public String nextId(Integer centerId, Integer workerId);

	/**
	 * 批量获得分布式ID
	 *
	 * @param count
	 *            count
	 * @return 分布式ID
	 */
	public List<String> batchNextId(Integer count);

	/**
	 * 批量获得分布式ID
	 *
	 * @param dataWorkId
	 *            数据中心ID | 机器ID ( 0 - 1023 )
	 * @param count
	 *            count
	 * @return 分布式ID
	 */
	public List<String> batchNextId(Integer dataWorkId, Integer count);

	/**
	 * 批量获得分布式ID
	 *
	 * @param centerId
	 *            数据中心ID ( 0 - 31 )
	 * @param workerId
	 *            机器ID ( 0 - 31 )
	 * @param count
	 *            count
	 * @return 分布式ID
	 */
	public List<String> batchNextId(Integer centerId, Integer workerId, Integer count);

}
