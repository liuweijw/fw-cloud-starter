package com.github.liuweijw.provider.rabbitmq.service;

import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.liuweijw.commons.utils.PublicHelper;
import com.github.liuweijw.provider.rabbitmq.beans.MqMessage;

/**
 * MQ 服务
 * 
 * @author liuweijw
 */
@Slf4j
@Component
public class MqServiceImpl implements MqService {

	/**
	 * 应用名称
	 */
	@Value("${spring.application.name}")
	private String			springApplicationName;

	/**
	 * 消息队列模板
	 */
	@Autowired
	private RabbitTemplate	rabbitTemplate;

	@Override
	public <T> void send(MqMessage<T> message, String exchange, String routingKey) {
		this.buildBaseMqMessage(exchange, routingKey, message);
		// 设置回调为当前类对象
		rabbitTemplate.setConfirmCallback(this);
		CorrelationData correlationId = new CorrelationData(message.getId());
		// 发送消息到消息队列
		rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message, correlationId);
	}

	/**
	 * 消息回调确认方法
	 * 
	 * @param correlationData
	 *            请求数据对象
	 * @param ack
	 *            是否发送成功
	 * @param cause
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("MqService.confirm 消息 {}，发送结果 {}, 其它cause {}", correlationData.getId(), ack, cause);
	}

	/**
	 * 构建MQ消息
	 * 
	 * @param exchange
	 *            交换机
	 * @param routingKey
	 *            路由key
	 * @param message
	 *            消息
	 * @return
	 *         返回原来message
	 */
	private <T> MqMessage<T> buildBaseMqMessage(String exchange, String routingKey, MqMessage<T> message) {
		message.setService(springApplicationName);
		message.setExchange(exchange);
		message.setRoutingKey(routingKey);
		if (PublicHelper.isEmpty(message.getExchange())) message.setExchange("");
		if (PublicHelper.isEmpty(message.getRoutingKey())) message.setRoutingKey("");
		if (PublicHelper.isEmpty(message.getId())) message.setId(UUID.randomUUID().toString());
		Date currentTime = new Date();
		message.setSentTime(currentTime);
		if (null == message.getCreatTime()) message.setCreatTime(currentTime);
		return message;
	}

}
