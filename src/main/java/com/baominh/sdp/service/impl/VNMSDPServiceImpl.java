/**
 *
 */
package com.baominh.sdp.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.baominh.sdp.common.Constants;
import com.baominh.sdp.entity.*;
import com.baominh.sdp.repository.jpa.*;
import com.baominh.sdp.utils.DateHelper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baominh.sdp.dto.ContentDto;
import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTResponseDto;
import com.baominh.sdp.dto.ServiceDto;
import com.baominh.sdp.dto.SmsuserDto;
import com.baominh.sdp.exception.BMException;
import com.baominh.sdp.exception.ErrorCode;
import com.baominh.sdp.service.VNMSDPService;
import com.baominh.sdp.utils.LoggingUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author HungDT
 *
 */
@Service
@Slf4j
public class VNMSDPServiceImpl implements VNMSDPService {

    @Value("${sdp.username}")
    private String username;

    @Value("${sdp.password}")
    private String password;

    @Value("${sdp.shortcode}")
    private String serviceAddress;

    @Value("${sdp.mtapi.url}")
    private String sdpurl;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SmsuserRepository smsUserRepository;

    @Autowired
    private SmslogRepository smsLogRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private MtqueueRepository mtqueueRepository;

    @Override
    public MTResponseDto sendSDPMT(MTRequestDto mtRequest) {
        log.info("Call send mt api: {}", sdpurl);
        log.info("Data: ", mtRequest.toHashMap().toString());
        List<NameValuePair> requestParams = new ArrayList<>();
        MTResponseDto mtResp = null;

        for (Map.Entry<String, String> entry : mtRequest.toHashMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            requestParams.add(new BasicNameValuePair(key, value));
        }

        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpPost post = new HttpPost(sdpurl);
            post.setEntity(new UrlEncodedFormEntity(requestParams));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            log.info("Return data:{}", result.toString());

            mapper = new ObjectMapper();

            mtResp = mapper.readValue(result.toString(), MTResponseDto.class);

        } catch (JsonParseException e) {
            throw new BMException(e, ErrorCode.SEND_MT_ERROR,
                    new StringBuilder(" parameter: ").append(LoggingUtils.objToStringIgnoreEx(mtRequest))
                            .append(" trace message.").append(e.getMessage()));
        } catch (JsonMappingException e) {
            throw new BMException(e, ErrorCode.SEND_MT_ERROR,
                    new StringBuilder(" parameter: ").append(LoggingUtils.objToStringIgnoreEx(mtRequest))
                            .append(" trace message.").append(e.getMessage()));
        } catch (IOException e) {
            throw new BMException(e, ErrorCode.SEND_MT_ERROR,
                    new StringBuilder(" parameter: ").append(LoggingUtils.objToStringIgnoreEx(mtRequest))
                            .append(" trace message.").append(e.getMessage()));
        }

        return mtResp;
    }

    @Override
    @Transactional
    public boolean sendSMS(ContentDto contentDto, SmsuserDto smsUserDto) {
        log.info("Send SMS: {}", LoggingUtils.objToStringIgnoreEx(contentDto));
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");

        ServiceEntity service = serviceRepository.findById(contentDto.getServiceID()).orElse(null);

        if (service != null) {
            log.info("Send SMS: {}, Content: {}", smsUserDto.getPhone(), contentDto.getContent());
            String transId = sdf.format(new Date());
            MTRequestDto mtReq = MTRequestDto.builder().transId(transId).username(username).password(password)
                    .time(sdf.format(new Date())).isdn(smsUserDto.getPhone()).serviceAddress(serviceAddress)
                    .categoryId(service.getSdpCategoryId().toString()).productId(service.getSdpProductId().toString())
                    .moid("0").message(contentDto.getContent()).unicode(1).flash(0).href("").build();

            MTResponseDto resp = sendSDPMT(mtReq);
            Smslog smslog = Smslog.builder().content(contentDto.getContent()).date(DateHelper.dateToUnixTime(new Date())).from(serviceAddress).to(smsUserDto.getPhone()).retryNumber(0)
                    .serviceID(contentDto.getServiceID()).type(Constants.SMS_TYPE.MT).build();
            if (resp.getStatus() == 0) {
                smslog.setStatus("success");
            } else {
                smslog.setStatus("fail");
            }

            smsLogRepository.save(smslog);
            return true;
        } else {
            log.info("ServiceId {} not exist!!", contentDto.getServiceID());
            return false;
        }
    }

    @Override
    @Transactional
    public void sendSMSDaily() {
        
    }

    @Override
    @Transactional
    public void getContentDaily() {
        log.info("Get Content Daily");

        //Lay danh sach Service -> content -> save to queue
        List<ServiceEntity> serviceEntityList = serviceRepository.getServiceEntityByEnable();
        Content content = contentRepository.getContentByServiceId(service.getId());

        List<Mtqueue> lstQueue = new ArrayList<>();
        for(ServiceEntity service : serviceEntityList) {
            Smsuser user = smsUserRepository.getSubsriberByServiceId(service.getId());
            Mtqueue mtqueue = Mtqueue.builder().isdn(user.getPhone()).serviceAddress(serviceAddress).content(content.getContent()).productId(service.getSdpProductId())
                    .categoryId(service.getSdpCategoryId()).description("MT Daily").createdDate(new Date()).status(1).build();
            lstQueue.add(mtqueue);
        }

        log.info("Insert {} record MT to Database.", lstQueue.size());

        if(lstQueue.size() > 0) {
            mtqueueRepository.saveAll(lstQueue);
        }
    }

}
