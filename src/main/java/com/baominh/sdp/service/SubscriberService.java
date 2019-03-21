/**
 * 
 */
package com.baominh.sdp.service;

/**
 * @author hungdt8
 *
 */
public interface SubscriberService {

    void register(String isdn, Integer sdpProductId);
    void unregister(String isdn, Integer sdpProductId);
    void subscription(String isdn, Integer sdpProductId);
}
