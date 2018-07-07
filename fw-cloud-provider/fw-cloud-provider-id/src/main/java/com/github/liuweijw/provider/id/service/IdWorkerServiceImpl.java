package com.github.liuweijw.provider.id.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.provider.config.properties.ConfigProperties;
import com.github.liuweijw.provider.id.IdWorkerFactory;

/**
 * 分布式id相关
 * 
 * @author liuweijw
 */
@Component
public class IdWorkerServiceImpl implements IdWorkerService {

	/**
	 * 分布式id工厂
	 */
	@Autowired
	private IdWorkerFactory		idWorkerFactory;

	/**
	 * 统一配置
	 */
	@Autowired
	private ConfigProperties	configProperties;

	/**
	 * 获得分布式ID
	 *
	 * @return 分布式ID
	 */
	@Override
	public String nextId() {
		Integer centerId = configProperties.getIdWorker().getCenterId();
		Integer workerId = configProperties.getIdWorker().getWorkerId();
		if (null == centerId || null == workerId)
			return idWorkerFactory.nextId();
		return this.nextId(centerId, workerId);
	}

	/**
	 * 获得分布式ID
	 *
	 * @param dataWorkId
	 *            数据中心ID | 机器ID ( 0 - 1023 )
	 * @return 分布式ID
	 */
	@Override
	public String nextId(Integer dataWorkId) {
		return idWorkerFactory.nextId(dataWorkId);
	}

	/**
	 * 获得分布式ID
	 *
	 * @param centerId
	 *            数据中心ID ( 0 - 31 )
	 * @param workerId
	 *            机器ID ( 0 - 31 )
	 * @return 分布式ID
	 */
	@Override
	public String nextId(Integer centerId, Integer workerId) {
		return idWorkerFactory.nextId(centerId, workerId);
	}

	/**
	 * 批量获得分布式ID
	 *
	 * @param count
	 *            count
	 * @return 分布式ID
	 */
	@Override
	public List<String> batchNextId(Integer count) {
		return idWorkerFactory.batchNextId(count);
	}

	/**
	 * 批量获得分布式ID
	 *
	 * @param dataWorkId
	 *            数据中心ID | 机器ID ( 0 - 1023 )
	 * @param count
	 *            count
	 * @return 分布式ID
	 */
	@Override
	public List<String> batchNextId(Integer dataWorkId, Integer count) {
		return idWorkerFactory.batchNextId(dataWorkId, count);
	}

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
	@Override
	public List<String> batchNextId(Integer centerId, Integer workerId, Integer count) {
		return idWorkerFactory.batchNextId(centerId, workerId, count);
	}

}
