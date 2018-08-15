package com.github.liuweijw.provider.redis.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.liuweijw.commons.utils.PublicHelper;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.provider.config.properties.ConfigProperties;
import com.github.liuweijw.provider.core.exception.ServiceRuntimeException;

import lombok.extern.slf4j.Slf4j;

/**
 * The class Redis service.
 *
 * @author liuweijw
 */
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {

	@Autowired
	private StringRedisTemplate	rt;

	@Autowired
	private ConfigProperties	fwConfigProperties;

	@Override
	public boolean hasKey(String key) {
		return rt.hasKey(key);
	}

	@Override
	public long getExpire(String key) {
		return rt.getExpire(key);
	}

	@Override
	public void expire(String key, long timeout, TimeUnit unit) {
		if (null == unit)
			throw new ServiceRuntimeException("TimeUnit is not null");

		if (timeout > 0) {
			rt.expire(key, timeout, unit);
		}
	}

	@Override
	public String get(String key) {
		String value = null;
		ValueOperations<String, String> ops = rt.opsForValue();
		if (rt.hasKey(key))
			value = ops.get(key);
		log.info("redis get. [OK] key={}, value={}", key, value);
		return value;
	}

	@Override
	public <T> T get(String key, Class<T> obj) {
		String value = get(key);
		if (null == value)
			return null;
		try {
			return JSONObject.parseObject(value, obj);
		} catch (Exception e) {
			log.error("redis get. [FAIL] key={}, value={}, JSON parseObject has error {}", key, value, e.getMessage());
			return null;
		}
	}

	@Override
	public void delete(String key) {
		rt.delete(key);
		log.info("redis delete. [OK] key={}", key);
	}

	@Override
	public void set(String key, String value) {
		set(key, value, fwConfigProperties.getRedis().getExpiration(), TimeUnit.SECONDS);
	}

	@Override
	public void set(String key, Serializable value) {
		set(key, JSONObject.toJSONString(value));
	}

	@Override
	public boolean setIfAbsent(String key, Serializable value) {
		Boolean b = rt.opsForValue().setIfAbsent(key, JSONObject.toJSONString(value));
		return null != b && b;
	}

	@Override
	public void set(String key, Serializable value, long timeout, TimeUnit unit) {
		set(key, JSONObject.toJSONString(value), timeout, unit);
	}

	@Override
	public void set(String key, String value, long timeout, TimeUnit unit) {
		if (StringHelper.isEmpty(key))
			throw new ServiceRuntimeException("Redis key is not null");

		ValueOperations<String, String> ops = rt.opsForValue();
		ops.set(key, value);
		this.expire(key, timeout, unit);
		log.info("redis set. [OK] key={}, value={}, timeout={}, unit={}", key, value, timeout, unit);
	}

	@Override
	public Set<String> getElements(String key) {
		Set<String> result;
		SetOperations<String, String> setOps = rt.opsForSet();
		result = setOps.members(key);
		log.info("redis get - 根据key获取所有元素值. [OK] key={}, value={}", key, result);
		return result;
	}

	@Override
	public Long add(String key, String... value) {
		return this.add(key, fwConfigProperties.getRedis().getExpiration(), TimeUnit.SECONDS, value);
	}

	@Override
	public Long add(String key, long timeout, TimeUnit unit, String... value) {
		SetOperations<String, String> setOps = rt.opsForSet();
		Long result = setOps.add(key, value);
		this.expire(key, timeout, unit);
		log.info("redis add - 向key里面添加元素, key={}, value={}, timeout={}, unit={}", key, value, timeout, unit);
		return result;
	}

	@Override
	public Long remove(String key, String... value) {
		SetOperations<String, String> setOps = rt.opsForSet();
		Long result = setOps.remove(key, (Object) value);
		log.info("redis remove - 根据key移除元素, key={}, value={}, result={}", key, value, result);
		return result;
	}

	@Override
	public <T> List<T> getHash(String key, Set<String> fields) {
		HashOperations<String, String, T> hash = rt.opsForHash();
		if (!rt.hasKey(key)) { return Collections.emptyList(); }
		List<T> values = hash.multiGet(key, fields);
		if (PublicHelper.isEmpty(values)) { return Collections.emptyList(); }
		log.info("redis getHash - 根据key获取所有给定字段的值. [OK] key={}, fields={}, values={}", key, fields, values);
		return values;

	}

	@Override
	public void setHash(String key, Map<String, Object> map) {
		setHash(key, map, fwConfigProperties.getRedis().getExpiration(), TimeUnit.SECONDS);
	}

	@Override
	public void setHash(String key, Map<String, Object> map, long timeout, TimeUnit unit) {
		HashOperations<String, String, Object> hash = rt.opsForHash();
		if (null == map || map.size() == 0)
			return;
		map.forEach((k, v) -> {
			if (!(v instanceof String)) {
				map.put(k, JSONObject.toJSONString(v));
			}
		});
		hash.putAll(key, map);
		this.expire(key, timeout, unit);
		log.info(
				"redis setHash - 同时将多个 field-value (域-值)对设置到哈希表 key 中. [ok] key={}, map={}, timeout={}, unit={}", key,
				map, timeout, unit);
	}

	@Override
	public Long removeHash(String key, String... hashKeys) {
		HashOperations<String, String, Object> hash = rt.opsForHash();
		Long result = hash.delete(key, (Object) hashKeys);
		log.info("redis removeHash- 删除一个或多个哈希表字段. [OK] key={}, hashKeys={}, result={}", key, hashKeys, result);
		return result;
	}

	@Override
	public <T> T getHash(String key, String field, Class<T> clazz) {
		HashOperations<String, String, T> hash = rt.opsForHash();
		if (!rt.hasKey(key))
			return null;
		T value = hash.get(key, field);
		if (PublicHelper.isEmpty(value))
			return null;
		log.info("redis getHash - 根据key获取给定字段的值. [OK] key={}, field={}, value={}", key, field, value);
		if (clazz == String.class)
			return value;
		try {
			return JSONObject.parseObject(String.valueOf(value), clazz);
		} catch (Exception e) {
			log.error(
					"redis getHash - 根据key获取给定字段的值. [OK] key={}, field={}, value={} 转换出现错误{}", key, field, value,
					e.getMessage());
			return null;
		}
	}

	@Override
	public String getHashValue(String key, String field) {
		return getHash(key, field, String.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getHashValueForMap(String key, String field) {
		return getHash(key, field, Map.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getHashValueForList(String key, String field) {
		return getHash(key, field, List.class);
	}

	@Override
	public Date getHashValueForDate(String key, String field) {
		return getHash(key, field, Date.class);
	}

	@Override
	public Integer getHashValueForInt(String key, String field) {
		return getHash(key, field, Integer.class);
	}

}
