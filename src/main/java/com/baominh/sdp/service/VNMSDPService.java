/**
 * 
 */
package com.baominh.sdp.service;

import com.baominh.sdp.dto.ContentDto;
import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTResponseDto;
import com.baominh.sdp.dto.SendSmsDto;
import com.baominh.sdp.dto.ServiceDto;
import com.baominh.sdp.dto.SmsuserDto;
import com.baominh.sdp.entity.Smsuser;

/**
 * @author HungDT
 *
 */
public interface VNMSDPService {
	MTResponseDto sendSDPMT(MTRequestDto mtRequest);
	
	void sendSMS(SendSmsDto sendSms);

	void sendSMSDaily(SendSmsDto sendSms);

	void getContentDaily(Integer serviceId);

	void prepareForSendMT();
	
//	void test();
}
