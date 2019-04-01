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

import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baominh.sdp.common.Constants;
import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTResponseDto;
import com.baominh.sdp.dto.SendSmsDto;
import com.baominh.sdp.entity.Content;
import com.baominh.sdp.entity.Mtqueue;
import com.baominh.sdp.entity.Schedule;
import com.baominh.sdp.entity.Schedulelist;
import com.baominh.sdp.entity.ServiceEntity;
import com.baominh.sdp.entity.Smslog;
import com.baominh.sdp.entity.Smsuser;
import com.baominh.sdp.exception.BMException;
import com.baominh.sdp.exception.ErrorCode;
import com.baominh.sdp.repository.jpa.ContentRepository;
import com.baominh.sdp.repository.jpa.MtqueueRepository;
import com.baominh.sdp.repository.jpa.ScheduleRepository;
import com.baominh.sdp.repository.jpa.SchedulelistRepository;
import com.baominh.sdp.repository.jpa.ServiceRepository;
import com.baominh.sdp.repository.jpa.SmslogRepository;
import com.baominh.sdp.repository.jpa.SmsuserRepository;
import com.baominh.sdp.service.VNMSDPService;
import com.baominh.sdp.utils.DateHelper;
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

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private SchedulelistRepository schedulelistRepository;

	@Autowired
	private RabbitMQPublisherImpl rabbitMqPublisherImpl;

	@Override
	public MTResponseDto sendSDPMT(MTRequestDto mtRequest) {
		log.info("Call send mt api: {}", sdpurl);
		MTResponseDto mtResp = null;

		HttpClient client = HttpClientBuilder.create().build();

		String request = LoggingUtils.objToStringIgnoreEx(mtRequest);

		try {
			StringEntity entity = new StringEntity(request);
			log.info("Data Request: {}", LoggingUtils.objToStringIgnoreEx(mtRequest));
			HttpPost post = new HttpPost(sdpurl);
			post.setEntity(entity);
			post.addHeader("Content-Type", "application/json");
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
	@RabbitListener(queues = "${sdp.rabbitmq.queue.mt}")
	public boolean sendSMS(SendSmsDto sendSms) {
		log.info("Entering to send SMS SDP");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");

		log.info("Send SMS: {}", LoggingUtils.objToStringIgnoreEx(sendSms));

		ServiceEntity service = serviceRepository.findById(sendSms.getServiceId()).orElse(null);
		
		String productId = "0";
		
		if(service == null) {
			productId = "2522";
		}

		String transId = sdf.format(new Date());
		MTRequestDto mtReq = MTRequestDto.builder().transId(transId).username(username).password(password)
				.time(sdf.format(new Date())).isdn(sendSms.getIsdn()).serviceAddress(sendSms.getServiceAddress())
				.categoryId("0").productId(productId)
				.moid(sendSms.getMoId()).message(sendSms.getContent()).unicode(1).flash(sendSms.getFlash())
				.href(sendSms.getHref()).build();

		MTResponseDto resp = sendSDPMT(mtReq);
		
//		MTResponseDto resp = null;
		Smslog smslog = Smslog.builder().content(sendSms.getContent()).date(DateHelper.dateToUnixTime(new Date()))
				.from(sendSms.getServiceAddress()).to("+" + sendSms.getIsdn()).retryNumber(0)
				.serviceID(sendSms.getServiceId()).type(Constants.SMS_TYPE.MT).build();
		if (resp.getStatus() == 0) {
			smslog.setStatus("success");
			smsLogRepository.save(smslog);
		} else {
			smslog.setStatus("fail");
			smsLogRepository.save(smslog);
			throw new BMException(null, ErrorCode.SEND_MT_ERROR,
					new StringBuilder().append("Status: ").append(resp.getStatus()).append(" | Message: ")
							.append(resp.getDescription()).append(" | TransId: ").append(resp.getTransId()));
		}

		log.info("Insert log: {}", LoggingUtils.objToStringIgnoreEx(smslog));

		return true;

	}

	@Override
	@Transactional
	@RabbitListener(queues = "${sdp.rabbitmq.queue.mtdaily}")
	public void sendSMSDaily(SendSmsDto sendSms) {
		log.info("Start send MT daily...");

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");

		ServiceEntity service = serviceRepository.findById(sendSms.getServiceId()).orElse(null);
		Smsuser smsuser = smsUserRepository.getSubscriberByIsdn(service.getId(), sendSms.getIsdn());
		if (smsuser != null) {
			String transId = sdf.format(new Date());
			MTRequestDto mtReq = MTRequestDto.builder().transId(transId).username(username).password(password)
					.time(sdf.format(new Date())).isdn(sendSms.getIsdn()).serviceAddress(sendSms.getServiceAddress())
					.categoryId("0").productId(service.getSdpProductId().toString())
					.moid("0").message(sendSms.getContent()).unicode(1).flash(0).href("").build();

			log.info("{} --> Send SMS Request - {} ", transId, LoggingUtils.objToStringIgnoreEx(mtReq));
			MTResponseDto resp = sendSDPMT(mtReq);
			log.info("{} --> Send SMS Response - {} ", transId, LoggingUtils.objToStringIgnoreEx(resp));
			Smslog smslog = Smslog.builder().content(sendSms.getContent()).date(DateHelper.dateToUnixTime(new Date()))
					.from(serviceAddress).to(sendSms.getIsdn()).retryNumber(0).serviceID(sendSms.getServiceId())
					.type(Constants.SMS_TYPE.MT).build();
			if (resp.getStatus() == 0) {
				smslog.setStatus("success");
			} else {
				smslog.setStatus("fail");
			}

			smsLogRepository.save(smslog);
		} else {
			log.info("Subscriber does not exist or inactive.");
		}

	}

	@Override
	@Transactional
	public void getContentDaily(Integer serviceId) {
		log.info("Get Content Daily");

		

		SendSmsDto sendSmsDto = null;
		Content content = contentRepository.getContentByServiceId(serviceId);
		if (content != null) {
			List<Smsuser> lstUser = smsUserRepository.getSubsriberByServiceId(serviceId);
			log.info("lstUser: {}", LoggingUtils.objToStringIgnoreEx(lstUser));

			for (Smsuser user : lstUser) {
				sendSmsDto = new SendSmsDto();
				sendSmsDto.setContent(content.getContent());
				sendSmsDto.setFlash(0);
				sendSmsDto.setHref("");
				sendSmsDto.setIsdn(user.getPhone());
				sendSmsDto.setMoId("0");
				sendSmsDto.setServiceAddress("568");
				sendSmsDto.setServiceId(serviceId);
				sendSmsDto.setUnicode(1);

				// Send to queue
				rabbitMqPublisherImpl.putToSMSDailyQueue(sendSmsDto);
			}

		} else {
			log.info("No content set for service: {}.", serviceId);
		}

		if (content != null) {
			content.setIsSend(1);
			contentRepository.save(content);
		}

	}

	@Override
	@Scheduled(fixedDelayString = "${preparemt.fixedDelay.in.milliseconds}")
	@Scheduled(fixedDelayString = "${preparemt.fixedRate.in.milliseconds}")
	public void prepareForSendMT() {
		log.info("Prepare content to sendMT");

		List<ServiceEntity> lstService = serviceRepository.getServiceEntityByEnable();

		for (ServiceEntity service : lstService) {
			List<Schedulelist> lstSchedulelist = schedulelistRepository.getAllScheduleListByServiceId(service.getId());
			if (lstSchedulelist.size() > 0) {

				for (Integer i = 0; i < lstSchedulelist.size(); i++) {

					Schedule schedule = scheduleRepository.findById(lstSchedulelist.get(i).getScheduleID())
							.orElse(null);
					if (schedule != null) {
						Calendar calendarNow = Calendar.getInstance();
						calendarNow.setTime(new Date());
						Integer hh = calendarNow.get(Calendar.HOUR_OF_DAY);
						Integer mm = calendarNow.get(Calendar.MINUTE);
						Integer day = calendarNow.get(Calendar.DAY_OF_WEEK) + 1;// 0 = monday -> 6 = sunday
						Integer week = calendarNow.get(Calendar.WEEK_OF_MONTH);
						Integer hhDb = Integer.parseInt(schedule.getHour().split(":")[0]);
						Integer mmDb = Integer.parseInt(schedule.getHour().split(":")[1]);
						if (schedule.getHour() != null && schedule.getDay() == null && schedule.getWeek() == null) {

							if (hh.intValue() == hhDb.intValue() && mm.intValue() == mmDb.intValue()) {
								getContentDaily(service.getId());
							}
						} else if (schedule.getHour() != null && schedule.getDay() != null
								&& schedule.getWeek() == null) {
							if (hhDb.intValue() == hh.intValue() && mm.intValue() == mmDb.intValue()
									&& day.intValue() == schedule.getDay()) {
								getContentDaily(service.getId());
							}
						} else if (schedule.getHour() != null && schedule.getDay() != null
								&& schedule.getWeek() != null) {
							if (hhDb.intValue() == hh.intValue() && mm.intValue() == mmDb.intValue()
									&& day.intValue() == schedule.getDay().intValue()
									&& week.intValue() == schedule.getWeek().intValue()) {
								getContentDaily(service.getId());
							}
						} else {
							log.info("No schedule for this service -> {}", service.getId());
						}

					} else {
						log.info("No schedule {}", service.getId());
					}
				}
			} else {
				log.info("No schedule {} - {}", service.getId(), service.getSyntaxRegister());
			}

		}
	}

//	@Override
//	@Scheduled(fixedDelay = 3000, initialDelay = 1000)
//	public void test() {
//		long now = System.currentTimeMillis() / 1000;
//	    System.out.println(
//	      "Fixed rate task with one second initial delay - " + now);
//		
//	}

}
