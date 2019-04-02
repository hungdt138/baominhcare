/**
 *
 */
package com.baominh.sdp.service.impl;

import com.baominh.sdp.dto.SubscriberRequestDto;
import com.baominh.sdp.entity.Charging;
import com.baominh.sdp.entity.Charginglog;
import com.baominh.sdp.entity.ServiceEntity;
import com.baominh.sdp.entity.Setting;
import com.baominh.sdp.entity.Smsuser;
import com.baominh.sdp.repository.jpa.ChargingLogRepository;
import com.baominh.sdp.repository.jpa.ChargingRepository;
import com.baominh.sdp.repository.jpa.ServiceRepository;
import com.baominh.sdp.repository.jpa.SettingRepository;
import com.baominh.sdp.repository.jpa.SmsuserRepository;
import com.baominh.sdp.service.SubscriberService;
import com.baominh.sdp.service.VNMSDPService;
import com.baominh.sdp.utils.DateHelper;
import com.baominh.sdp.utils.LoggingUtils;
import com.baominh.sdp.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private ChargingRepository chargingRepository;

    @Autowired
    private SettingRepository settingRepository;

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
        log.info("subscription subscriber. isdn = {}, productId = {}", subRequest.getIsdn(), subRequest.getProductid());
        ServiceEntity service = serviceRepository.getServiceBySdpProductId(Integer.valueOf(subRequest.getProductid()));
        if (service != null) {
            Smsuser user = smsUserRepository.getSubscriberByIsdn(service.getId(), "+" + subRequest.getIsdn());
            if (user != null) {
                user.setLastCharge(DateHelper.dateToUnixTime(new Date()));
                smsUserRepository.save(user);

                Charginglog chargingLog = Charginglog.builder().price(Integer.valueOf(subRequest.getAmount()))
                        .date(DateHelper.dateToUnixTime(new Date())).phone("+" + subRequest.getIsdn()).chargeTime(1)
                        .serviceID(service.getId()).reason("SDP VNM Charging")
                        .chargingID(Integer.valueOf(subRequest.getTransid())).build();
                chargingLogRepository.save(chargingLog);

                log.info("Subscription user {} successfuly.", subRequest.getIsdn());
            } else {
                log.info("User does not exist on system.");
            }

        } else {
            log.info("Service not found with SDPPRODUCTID = ", subRequest.getProductid());
        }
    }

    @Override
    public List<String> checkSubscription(String isdn) {
        log.info("Check subscription isdn: {}", isdn);

        List<String> lstSmsContent = new ArrayList<>();

        List<Smsuser> lstSub = smsUserRepository.getAllSubscriptionByIsdn("+" + isdn);

        List<Setting> setting = settingRepository.findAll();

        String ktdv_success = setting.get(0).getKtdvSuccessMsg();
        String ktdv_fail = setting.get(0).getKtdvFailMsg();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

        if (lstSub.size() > 0) {

            for (int i = 0; i < lstSub.size(); i++) {
                ServiceEntity service = serviceRepository.findById(lstSub.get(i).getServiceID()).orElse(null);
                Charging charging = chargingRepository.findById(service.getChargingID()).orElse(null);
                ktdv_success = ktdv_success.replace("$name", StringUtils.removeAccent(service.getName())).replace("$date", sdf.format(DateHelper.unixTimeToDate(lstSub.get(i).getRegisterDate())))
                        .replace("$unreg", service.getSyntaxUnregister()).replace("$type", lstSub.get(i).getRegisterType()).replace("$price", String.valueOf(charging.getPrice()));

                log.info("MT: {}", ktdv_success);

                lstSmsContent.add(ktdv_success);
            }
        } else {
            lstSmsContent.add(ktdv_fail);
        }

        return lstSmsContent;
    }
}
