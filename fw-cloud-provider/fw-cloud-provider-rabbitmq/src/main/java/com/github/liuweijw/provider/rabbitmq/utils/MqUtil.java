package com.github.liuweijw.provider.rabbitmq.utils;

import java.util.Date;

import com.github.liuweijw.provider.rabbitmq.beans.MqMessage;

/**
 * MQ 工具构建
 * 
 * @author liuweijw
 */
public class MqUtil {

	/**
	 * 构建消息
	 * 
	 * @param T
	 *            body 消息体
	 * @return
	 *         消息
	 */
	public static <T> MqMessage<T> of(T body) {
		return of(null, body);
	}

	/**
	 * 构建消息
	 * 
	 * @param exchange
	 *            交换机
	 * @param T
	 *            body 消息体
	 * @return
	 *         消息
	 */
	public static <T> MqMessage<T> of(String exchange, T body) {
		return of(null, null, body);
	}

	/**
	 * 构建消息
	 * 
	 * @param exchange
	 *            交换机
	 * @param routingKey
	 *            路由key
	 * @param T
	 *            body 消息体
	 * @return
	 *         消息
	 */
	public static <T> MqMessage<T> of(String exchange, String routingKey, T body) {
		MqMessage<T> mqMessage = new MqMessage<T>();
		mqMessage.setCreatTime(new Date());
		mqMessage.setExchange(exchange);
		mqMessage.setRoutingKey(routingKey);
		mqMessage.setBody(body);
		return mqMessage;
	}
}
