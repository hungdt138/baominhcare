/**
 * 
 */
package com.baominh.sdp.service.impl;

import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baominh.sdp.common.Constants;
import com.baominh.sdp.dto.SendSmsDto;
import com.baominh.sdp.service.RabbitMQPublisher;

import lombok.extern.slf4j.Slf4j;

/**
 * @author HungDT
 *
 */
@Service
@Slf4j
public class RabbitMQPublisherImpl implements RabbitMQPublisher {

	@Value("${sdp.rabbitmq.routingkey.mt}")
	private String sendmt_key;
	@Value("${sdp.rabbitmq.routingkey.mtdaily}")
	private String sendmt_daily_key;
	@Value("${sdp.rabbitmq.key.mt.error}")
	private String sendmt_error_key;

	@Value("${sdp.rabbitmq.exchange}")
	private String exchange;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void putToSMSQueue(SendSmsDto sendSms) {
		rabbitTemplate.convertAndSend(exchange, sendmt_key, sendSms);
		log.info("Success send to queue...");
	}

	@Override
	public void putToSMSDailyQueue(SendSmsDto sendSms) {
		rabbitTemplate.convertAndSend(exchange, sendmt_daily_key, sendSms);
		log.info("Success send to queue...");
	}

}
