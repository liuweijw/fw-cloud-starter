package com.github.liuweijw.provider.rabbitmq.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import lombok.ToString;

/**
 * MQ 消息bean
 * 
 * @param <T>
 *            T 消息bean
 * @author liuweijw
 */
@Data
@ToString
public class MqMessage<T> implements Serializable {

	private static final long	serialVersionUID	= -681489014049434720L;

	/**
	 * 消息ID
	 */
	private String				id;

	/**
	 * 发送服务器
	 */
	private String				service;

	/**
	 * 交换机
	 */
	private String				exchange;

	/**
	 * 路由键
	 */
	private String				routingKey;

	/**
	 * 创建时间
	 */
	private Date				creatTime;

	/**
	 * 消息发送时间
	 */
	private Date				sentTime;

	/**
	 * 消息头
	 */
	private Map<String, String>	header;

	/**
	 * 消息体
	 */
	private T					body;

	/**
	 * 构造函数
	 */
	public MqMessage() {

	}

	public MqMessage(Map<String, String> header, T body) {
		this.id = UUID.randomUUID().toString();
		this.creatTime = new Date();
		this.header = header;
		this.body = body;
	}

	public MqMessage(T body) {
		this(null, body);
	}

}
