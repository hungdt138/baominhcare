/**
 * 
 */
package com.baominh.sdp.service;

import org.springframework.amqp.core.Message;

import com.baominh.sdp.dto.SendSmsDto;

/**
 * @author HungDT
 *
 */
public interface RabbitMQPublisher {
	void putToSMSQueue(SendSmsDto sendSms);

	void putToSMSDailyQueue(SendSmsDto sendSms);
}
