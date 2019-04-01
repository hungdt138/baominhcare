/**
 * 
 */
package com.baominh.sdp.service;

import java.util.List;

import com.baominh.sdp.dto.SubscriberRequestDto;
import com.baominh.sdp.entity.Smsuser;

/**
 * @author hungdt8
 *
 */
public interface SubscriberService {

	void register(String isdn, Integer sdpProductId);

	void unregister(String isdn, Integer sdpProductId);

	void subscription(SubscriberRequestDto subRequest);

	List<String> checkSubscription(String isdn);
}
