package com.github.liuweijw.provider.id;

import lombok.extern.slf4j.Slf4j;

import com.github.liuweijw.provider.core.constant.IdWorkerConstant;
import com.github.liuweijw.provider.core.exception.ServiceRuntimeException;

/**
 * 分布式id生成器 <br/>
 * 基于twiter的snowflake https://www.cnblogs.com/relucent/p/4955340.html
 * 
 * @author liuweijw
 */
@Slf4j
public class IdWorker {

	/**
	 * 机器ID( 0 - 31 )
	 */
	private long	workerId;

	/**
	 * 数据中心ID( 0 - 31 )
	 */
	private long	centerId;

	/**
	 * 序列号( 0 - 4095)
	 */
	private long	sequence		= 0L;

	/**
	 * 上次生产id时间戳
	 */
	private long	lastTimestamp	= -1L;

	/**
	 * 构造函数
	 *
	 * @param workerId
	 *            workerId
	 * @param centerId
	 *            centerId
	 */
	public IdWorker(long workerId, long centerId) {
		if (workerId > IdWorkerConstant.MAX_WORKER_ID || workerId < 0)
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0", IdWorkerConstant.MAX_WORKER_ID));
		if (centerId > IdWorkerConstant.MAX_DATACENTER_ID || centerId < 0)
			throw new IllegalArgumentException(String.format(
					"datacenter Id can't be greater than %d or less than 0", IdWorkerConstant.MAX_DATACENTER_ID));
		this.workerId = workerId;
		this.centerId = centerId;
		log.info("centerId:{},workerId:{}", this.centerId, this.workerId);
	}

	/**
	 * 下一个ID
	 *
	 * @return ID
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp)
			throw new ServiceRuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & IdWorkerConstant.SEQUENCE_MASK;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		return ((timestamp - IdWorkerConstant.TWEPOCH) << IdWorkerConstant.TIMESTAMP_LEFT_SHIFT) | (centerId << IdWorkerConstant.DATACENTER_ID_SHIFT)
				| (workerId << IdWorkerConstant.WORKER_ID_SHIFT) | sequence;
	}

	/**
	 * 获得下一个毫秒数
	 *
	 * @param lastTimestampParam
	 *            lastTimestampParam
	 * @return 下一个毫秒数
	 */
	protected long tilNextMillis(long lastTimestampParam) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestampParam) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 获得当前时间毫秒数
	 *
	 * @return 当前时间毫秒数
	 */
	protected long timeGen() {
		return System.currentTimeMillis();
	}

}
