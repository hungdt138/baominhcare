/**
 * 
 */
package com.baominh.sdp.service;

import com.baominh.sdp.dto.SubscriberRequestDto;

/**
 * @author hungdt8
 *
 */
public interface SubscriberService {

    void register(String isdn, Integer sdpProductId);
    void unregister(String isdn, Integer sdpProductId);
    void subscription(SubscriberRequestDto subRequest);
}
