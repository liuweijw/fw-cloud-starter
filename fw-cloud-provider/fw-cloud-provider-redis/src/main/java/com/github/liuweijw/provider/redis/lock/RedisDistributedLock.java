package com.github.liuweijw.provider.redis.lock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.liuweijw.provider.redis.service.RedisService;

/**
 * 分布式redis 锁
 * 
 * @author liuweijw
 */
@Slf4j
@Component
public class RedisDistributedLock {

	/**
	 * 分布式锁前缀
	 */
	private static final String	DISTRIBUTED_LOCK_KEY_PREFIX	= ":DISTRIBUTED_LOCK:";

	/**
	 * redis 服务
	 */
	@Autowired
	private RedisService		redisService;

	/**
	 * 项目运行环境
	 */
	@Value("${spring.profiles.active}")
	private String				profiles;

	/**
	 * lockAndGet
	 *
	 * @param key
	 *            key 设置key
	 * @param timeout
	 *            timeout 超时时间
	 * @param timeUnit
	 *            timeUnit
	 * @param lockValue
	 *            lockValue 值
	 * @return RedisLockResult 返回值
	 */
	public RedisLockResult lockAndGet(String key, long timeout, TimeUnit timeUnit, String lockValue) {
		RedisLockResult lockResult = new RedisLockResult();
		// 获得key
		String distributedKey = this.getRedisDistributedKey(key);
		// 检查是否是死锁
		if (redisService.hasKey(distributedKey)) {
			// 获得缓存过期时间
			long expiration = redisService.getExpire(distributedKey);
			// 如果等于-1,则代表死锁
			if (expiration == -1) {
				redisService.delete(distributedKey);
			} else {
				String cacheValue = redisService.get(key);
				lockResult.setLock(false);
				lockResult.setValue(cacheValue == null ? null : cacheValue);
			}
		} else {
			// 创建锁
			boolean lock = redisService.setIfAbsent(key, lockValue);
			// 锁成功
			if (lock) {
				redisService.expire(key, timeout, timeUnit);
				lockResult.setLock(true);
				lockResult.setValue(lockValue);
			} else {
				String cacheValue = redisService.get(key);
				lockResult.setLock(false);
				lockResult.setValue(cacheValue == null ? null : cacheValue);
			}
		}
		log.info("RedisDistributedLock : lock key : {} : {}", distributedKey, lockResult.toString());
		return lockResult;
	}

	/**
	 * 给Redis key 加锁
	 *
	 * @param key
	 *            key 设置key
	 * @param timeout
	 *            timeout 超时时间
	 * @param timeUnit
	 *            timeUnit
	 * @param lockValue
	 *            lockValue 必须实现Serializable接口
	 * @return boolean
	 */
	public boolean lock(String key, long timeout, TimeUnit timeUnit, Serializable lockValue) {
		String distributedKey = this.getRedisDistributedKey(key);
		// 检查是否是死锁
		if (redisService.hasKey(distributedKey)) {
			// 获得缓存过期时间
			long expiration = redisService.getExpire(distributedKey);
			// 如果等于-1,则代表死锁
			if (expiration == -1) {
				redisService.delete(key);
			}
		}
		// 创建锁
		boolean lock = redisService.setIfAbsent(key, lockValue);
		// 锁成功
		if (lock) {
			redisService.expire(key, timeout, timeUnit);
		}
		log.info("RedisDistributedLock : lock key : {} : {}", distributedKey, lock);
		return lock;
	}

	/**
	 * 给redis key 解锁
	 *
	 * @param key
	 *            key 设置key
	 */
	public void unlock(String key) {
		String distributedKey = this.getRedisDistributedKey(key);
		redisService.delete(distributedKey);
		log.info("RedisDistributedLock : unlock key : {}", distributedKey);
	}

	/**
	 * 获取组合key
	 */
	public String getRedisDistributedKey(String key) {
		return profiles + DISTRIBUTED_LOCK_KEY_PREFIX + key;
	}

}
