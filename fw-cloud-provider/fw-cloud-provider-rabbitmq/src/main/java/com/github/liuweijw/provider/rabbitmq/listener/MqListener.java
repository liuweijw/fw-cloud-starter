package com.github.liuweijw.provider.rabbitmq.listener;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import com.github.liuweijw.provider.rabbitmq.beans.MqMessage;

/**
 * MQ 消息监听
 * 
 * @author liuweijw
 * @param <T>
 */
public interface MqListener<T> {

	/**
	 * 处理消息
	 *
	 * @param message
	 *            消息
	 */
	void process(MqMessage<T> message);

	/**
	 * 将队列绑定到指定交换机routingKey上
	 * 
	 * @param queue
	 *            需要初始化队列
	 * @param directExchange
	 *            需要初始化队列交换机
	 * @param routingKey
	 *            需要初始化路由key
	 */
	void binding(Queue queue, DirectExchange directExchange, String routingKey);

}
