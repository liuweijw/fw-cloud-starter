package com.github.liuweijw.provider.redis.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The interface Redis service.
 *
 * @author liuweijw
 */
public interface RedisService {

	/**
	 * 是否存在缓存key.
	 *
	 * @param key
	 *            缓存 key
	 */
	boolean hasKey(String key);

	/**
	 * 给缓存key设置过期时间
	 * 
	 * @param key
	 *            缓存 key
	 * @param timeout
	 *            缓存超时时间
	 * @param unit
	 *            缓存超时时间单位
	 */
	void expire(String key, long timeout, TimeUnit unit);

	/**
	 * 通过缓存key获取过期时间
	 * 
	 * @param key
	 *            缓存 key
	 * @param 缓存超时时间
	 */
	long getExpire(String key);

	/**
	 * 获取缓存key的值.
	 *
	 * @param key
	 *            缓存 key
	 * @return the key 值
	 */
	String get(String key);

	/**
	 * 获取key值.
	 *
	 * @param key
	 *            缓存 key
	 * @return the key T 对象值
	 */
	<T> T get(String key, Class<T> obj);

	/**
	 * 删除 key.
	 *
	 * @param key
	 *            缓存 key
	 */
	void delete(String key);

	/**
	 * 设置 key - value.
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 */
	void set(String key, String value);

	/**
	 * 设置 key - value.
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 */
	void set(String key, Serializable value);

	/**
	 * 设置 key - value.
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 */
	boolean setIfAbsent(String key, Serializable value);

	/**
	 * 设置 key - value.
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 * @param timeout
	 *            缓存 timeout
	 * @param unit
	 *            缓存 unit
	 */
	void set(String key, String value, final long timeout, final TimeUnit unit);

	/**
	 * 设置 key - value.
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 * @param timeout
	 *            缓存 timeout
	 * @param unit
	 *            缓存 unit
	 */
	void set(String key, Serializable value, final long timeout, final TimeUnit unit);

	/**
	 * 返回集合中的所有成员值
	 *
	 * @param key
	 *            缓存 key
	 * @return Get all elements of set at key.
	 */
	Set<String> getElements(String key);

	/**
	 * 向集合添加一个或多个成员
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 * @return 缓存个数
	 */
	Long add(String key, String... value);

	/**
	 * 设置 key - value.
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 * @param timeout
	 *            缓存 timeout
	 * @param unit
	 *            缓存 unit
	 */
	Long add(String key, final long timeout, final TimeUnit unit, String... value);

	/**
	 * 移除集合中一个或多个成员
	 *
	 * @param key
	 *            缓存 key
	 * @param value
	 *            缓存 value
	 * @return 缓存个数
	 */
	Long remove(String key, String... value);

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中
	 *
	 * @param key
	 *            the key
	 * @param map
	 *            the map map中的Object对象都会转为JSON存储
	 */
	void setHash(String key, Map<String, Object> map);

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中,此次超时时间只能针对Hash顶层有效
	 *
	 * @param key
	 *            the key
	 * @param map
	 *            the map map中的Object对象都会转为JSON存储
	 * @param timeout
	 *            缓存 timeout
	 * @param unit
	 *            缓存 unit
	 */
	void setHash(String key, Map<String, Object> map, final long timeout, final TimeUnit unit);

	/**
	 * 删除一个或多个哈希表字段
	 *
	 * @param key
	 *            the key
	 * @param hashKeys
	 *            the hash keys
	 * @return the long
	 */
	Long removeHash(String key, String... hashKeys);

	/**
	 * 获取所有给定字段的值
	 *
	 * @param <T>
	 *            the type parameter
	 * @param key
	 *            缓存hash key
	 * @param fields
	 *            缓存hash fields
	 * @return the value by fields
	 */
	<T> List<T> getHash(String key, Set<String> fields);

	/**
	 * 获取所有给定字段的值
	 *
	 * @param <T>
	 *            the type parameter
	 * @param key
	 *            缓存hash key
	 * @param field
	 *            缓存hash field
	 * @return the value by field
	 */
	<T> T getHash(String key, String field, Class<T> clazz);

	/**
	 * 获取所有给定的字段的值，默认返回String类型
	 * 
	 * @param key
	 *            缓存hash key
	 * @param field
	 *            缓存hash field
	 * @return the value by field (conversion to String)
	 */
	String getHashValue(String key, String field);

	/**
	 * 获取所有给定的字段的值，默认返回Map<String, String>类型
	 * 
	 * @param key
	 *            缓存hash key
	 * @param field
	 *            缓存hash field
	 * @return the value by field (conversion to Map<String, String>)
	 */
	Map<String, String> getHashValueForMap(String key, String field);

	/**
	 * 获取所有给定的字段的值，默认返回List<String>类型
	 * 
	 * @param key
	 *            缓存hash key
	 * @param field
	 *            缓存hash field
	 * @return the value by field (conversion to List<String>)
	 */
	List<String> getHashValueForList(String key, String field);

	/**
	 * 获取所有给定的字段的值，默认返回Date类型
	 * 
	 * @param key
	 *            缓存hash key
	 * @param field
	 *            缓存hash field
	 * @return the value by field (conversion to Date)
	 */
	Date getHashValueForDate(String key, String field);

	/**
	 * 获取所有给定的字段的值，默认返回Integer类型
	 * 
	 * @param key
	 *            缓存hash key
	 * @param field
	 *            缓存hash field
	 * @return the value by field (conversion to Integer)
	 */
	Integer getHashValueForInt(String key, String field);
}
