/**
 * 
 */
package com.baominh.sdp.service;

import com.baominh.sdp.dto.ContentDto;
import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTResponseDto;
import com.baominh.sdp.dto.ServiceDto;
import com.baominh.sdp.dto.SmsuserDto;
import com.baominh.sdp.entity.Smsuser;

/**
 * @author HungDT
 *
 */
public interface VNMSDPService {
	MTResponseDto sendSDPMT(MTRequestDto mtRequest);
	
	boolean sendSMS(ContentDto contentDto, SmsuserDto smsUserDto);

	void sendSMSDaily();

	void getContentDaily();
}
