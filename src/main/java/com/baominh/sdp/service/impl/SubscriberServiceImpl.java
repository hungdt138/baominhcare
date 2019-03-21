/**
 * 
 */
package com.baominh.sdp.service.impl;

import com.baominh.sdp.entity.ServiceEntity;
import com.baominh.sdp.entity.Smsuser;
import com.baominh.sdp.repository.jpa.ServiceRepository;
import com.baominh.sdp.repository.jpa.SmsuserRepository;
import com.baominh.sdp.service.SubscriberService;
import com.baominh.sdp.service.VNMSDPService;
import com.baominh.sdp.utils.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author hungdt8
 *
 */
@Service
@Slf4j
public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SmsuserRepository smsUserRepository;

    @Autowired
    private VNMSDPService vnmSdpService;

    @Override
    @Transactional
    public void register(String isdn, Integer sdpProductId) {
        log.info("Register subscriber. isdn = {}, productId = {}",isdn, sdpProductId);
        ServiceEntity service = serviceRepository.getServiceBySdpProductId(sdpProductId);
        if(service != null) {
            Smsuser user = Smsuser.builder().active(1).phone("+" + isdn).registerDate(DateHelper.dateToUnixTime(new Date())).registerType("SDP")
                    .serviceID(service.getId()).build();
            smsUserRepository.save(user);
            log.info("Save user {} successfuly.", isdn);

            vnmSdpService.sendSMS(isdn, service.getId());

        } else {
            log.info("Service not found with SDPPRODUCTID = ",sdpProductId);
        }
    }

    @Override
    public void unregister(String isdn, Integer sdpProductId) {
        log.info("unregister subscriber. isdn = {}, productId = {}",isdn, sdpProductId);
        ServiceEntity service = serviceRepository.getServiceBySdpProductId(sdpProductId);
        if(service != null) {
            Smsuser user = smsUserRepository.getSubscriberByIsdn(service.getId(),"+" + isdn);
            if(user != null) {
                user.setActive(0);
                user.setUnregisterDate(DateHelper.dateToUnixTime(new Date()));
                smsUserRepository.save(user);

                log.info("Unregister user {} successfuly.", isdn);
            } else {
                log.info("User does not exist on system.");
            }

        } else {
            log.info("Service not found with SDPPRODUCTID = ",sdpProductId);
        }
    }

    @Override
    public void subscription(String isdn, Integer sdpProductId) {
        log.info("subscription subscriber. isdn = {}, productId = {}",isdn, sdpProductId);
        ServiceEntity service = serviceRepository.getServiceBySdpProductId(sdpProductId);
        if(service != null) {
            Smsuser user = smsUserRepository.getSubscriberByIsdn(service.getId(),"+" + isdn);
            if(user != null) {
                user.setLastCharge(DateHelper.dateToUnixTime(new Date()));
                smsUserRepository.save(user);

                log.info("Unregister user {} successfuly.", isdn);
            } else {
                log.info("User does not exist on system.");
            }

        } else {
            log.info("Service not found with SDPPRODUCTID = ",sdpProductId);
        }
    }
}
