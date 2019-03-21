/**
 * 
 */
package com.baominh.sdp.service.impl;

import com.baominh.sdp.dto.SubscriberRequestDto;
import com.baominh.sdp.entity.Charginglog;
import com.baominh.sdp.entity.ServiceEntity;
import com.baominh.sdp.entity.Smsuser;
import com.baominh.sdp.repository.jpa.ChargingLogRepository;
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
	
	@Autowired
	private ChargingLogRepository chargingLogRepository;

	@Override
	@Transactional
	public void register(String isdn, Integer sdpProductId) {
		log.info("Register subscriber. isdn = {}, productId = {}", isdn, sdpProductId);
		ServiceEntity service = serviceRepository.getServiceBySdpProductId(sdpProductId);
		if (service != null) {
			Smsuser subscriber = smsUserRepository.getSubscriberByIsdn(service.getId(), "+" + isdn);
			if (subscriber != null && subscriber.getActive() == 1) {
				subscriber.setUnregisterDate(DateHelper.dateToUnixTime(new Date()));
				subscriber.setActive(0);
				smsUserRepository.save(subscriber);
			}
			Smsuser user = Smsuser.builder().active(1).phone("+" + isdn)
					.registerDate(DateHelper.dateToUnixTime(new Date())).registerType("SDP").serviceID(service.getId())
					.build();
			smsUserRepository.save(user);
			log.info("Save user {} successfuly. serciceId = {}", isdn, service.getId());

			// vnmSdpService.sendSMS(isdn, service.getId());

		} else {
			log.info("Service not found with SDPPRODUCTID = ", sdpProductId);
		}
	}

	@Override
	public void unregister(String isdn, Integer sdpProductId) {
		log.info("unregister subscriber. isdn = {}, productId = {}", isdn, sdpProductId);
		ServiceEntity service = serviceRepository.getServiceBySdpProductId(sdpProductId);
		if (service != null) {
			Smsuser user = smsUserRepository.getSubscriberByIsdn(service.getId(), "+" + isdn);
			if (user != null) {
				user.setActive(0);
				user.setUnregisterDate(DateHelper.dateToUnixTime(new Date()));
				smsUserRepository.save(user);

				log.info("Unregister user {} successfuly.", isdn);
			} else {
				log.info("User does not exist on system.");
			}

		} else {
			log.info("Service not found with SDPPRODUCTID = ", sdpProductId);
		}
	}

	@Override
    public void subscription(SubscriberRequestDto subRequest) {
        log.info("subscription subscriber. isdn = {}, productId = {}",subRequest.getIsdn(), subRequest.getProductid());
        ServiceEntity service = serviceRepository.getServiceBySdpProductId(Integer.valueOf(subRequest.getProductid()));
        if(service != null) {
            Smsuser user = smsUserRepository.getSubscriberByIsdn(service.getId(),"+" + subRequest.getIsdn());
            if(user != null) {
                user.setLastCharge(DateHelper.dateToUnixTime(new Date()));
                smsUserRepository.save(user);

                
                Charginglog chargingLog = Charginglog.builder().price(Integer.valueOf(subRequest.getAmount())).date(DateHelper.dateToUnixTime(new Date()))
                		.phone("+"+subRequest.getIsdn()).chargeTime(1).serviceID(service.getId()).reason("SDP VNM Charging").chargingID(Integer.valueOf(subRequest.getTransid())).build();
                chargingLogRepository.save(chargingLog);
                
                log.info("Subscription user {} successfuly.", subRequest.getIsdn());
            } else {
                log.info("User does not exist on system.");
            }

        } else {
            log.info("Service not found with SDPPRODUCTID = ",subRequest.getProductid());
        }
    }
}
