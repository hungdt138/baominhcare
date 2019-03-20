/**
 * 
 */
package com.baominh.sdp.service;

import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTResponseDto;

/**
 * @author HungDT
 *
 */
public interface VNMSDPService {
	MTResponseDto sendSDPMT(MTRequestDto mtRequest);
}
