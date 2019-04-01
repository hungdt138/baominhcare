/**
 * 
 */
package com.baominh.sdp.controller;

import java.util.List;

import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baominh.sdp.common.Constants;
import com.baominh.sdp.dto.MORequestDto;
import com.baominh.sdp.dto.ResponseDto;
import com.baominh.sdp.dto.SendSmsDto;
import com.baominh.sdp.dto.SubscriberRequestDto;
import com.baominh.sdp.repository.jpa.ContentRepository;
import com.baominh.sdp.repository.jpa.ServiceRepository;
import com.baominh.sdp.repository.jpa.SettingRepository;
import com.baominh.sdp.service.SubscriberService;
import com.baominh.sdp.service.VNMSDPService;
import com.baominh.sdp.service.impl.RabbitMQPublisherImpl;
import com.baominh.sdp.utils.LoggingUtils;

/**
 * @author hungdt8
 *
 */
@RestController
@RequestMapping("/api/vnm")
public class VNMSDPController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubscriberService subscriberService;

	@Autowired
	private VNMSDPService vnmSdpService;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private RabbitMQPublisherImpl rabbitMqPublisherImpl;

	@Autowired
	private SettingRepository settingRepository;

	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseDto checkStatus() {
		logger.info("Check status done ...");
		ResponseDto resp = new ResponseDto();
		resp.setDescription("Success");
		resp.setStatus("0");
		return resp;
	}

	@RequestMapping(value = "/subscriber", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseDto updateSubscriber(@PathParam("id") String id, @PathParam("transid") String transid,
			@PathParam("isdn") String isdn, @PathParam("serviceaddress") String serviceaddress,
			@PathParam("categoryid") String categoryid, @PathParam("productid") String productid,
			@PathParam("command") String command, @PathParam("amount") String amount,
			@PathParam("expiration") String expiration, @PathParam("hash") String hash) {

		SubscriberRequestDto subRequest = SubscriberRequestDto.builder().id(id).transid(transid).isdn(isdn)
				.serviceaddress(serviceaddress).categoryid(categoryid).productid(productid).command(command)
				.amount(amount).expiration(expiration).hash(hash).build();

		logger.info("SDP Request: {}", LoggingUtils.objToStringIgnoreEx(subRequest));

		if (subRequest.getCommand().equalsIgnoreCase(Constants.SDP_COMMAND.REGISTER)) {
			subscriberService.register(subRequest.getIsdn(), Integer.valueOf(subRequest.getProductid()));
//			ServiceEntity service = serviceRepository
//					.getServiceBySdpProductId(Integer.valueOf(subRequest.getProductid()));
//
//			Content content = contentRepository.getLatestContentByServiceId(service.getId());
//
//			SendSmsDto sendSms = SendSmsDto.builder().content(content.getContent()).flash(0).href("")
//					.isdn(subRequest.getIsdn()).moId("0").serviceAddress(serviceaddress).serviceId(service.getId())
//					.build();
//
//			rabbitMqPublisherImpl.putToSMSQueue(sendSms);
		} else if (subRequest.getCommand().equalsIgnoreCase(Constants.SDP_COMMAND.SUBSCRIPTION)) {
			subscriberService.subscription(subRequest);
		} else { // unregister or cancel
			subscriberService.unregister(subRequest.getIsdn(), Integer.valueOf(subRequest.getProductid()));
		}

		ResponseDto resp = new ResponseDto();
		resp.setDescription("Success");
		resp.setStatus("0");
		return resp;
	}

	@RequestMapping(value = "/moevent", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseDto moEvent(@PathParam("id") String id, @PathParam("transid") String transid,
			@PathParam("isdn") String isdn, @PathParam("serviceaddress") String serviceaddress,
			@PathParam("categoryid") String categoryid, @PathParam("productid") String productid,
			@PathParam("moid") String moid, @PathParam("amount") String amount, @PathParam("message") String message,
			@PathParam("hash") String hash) {

		MORequestDto moRequest = MORequestDto.builder().id(id).transid(transid).isdn(isdn)
				.serviceaddress(serviceaddress).categoryid(categoryid).productid(productid).moid(moid).amount(amount)
				.message(message).hash(hash).build();

		logger.info("SDP Request: {}", LoggingUtils.objToStringIgnoreEx(moRequest));

		if (Constants.SDP_MO.KTDV.equalsIgnoreCase(message)) {
			List<String> lstSubContentNoti = subscriberService.checkSubscription(isdn);

			SendSmsDto sendSms = null;

			for (int i = 0; i < lstSubContentNoti.size(); i++) {
				sendSms = SendSmsDto.builder().content(lstSubContentNoti.get(i)).flash(0).href("").isdn(isdn).moId(moid)
						.serviceAddress("568").serviceId(0).unicode(1).build();
				rabbitMqPublisherImpl.putToSMSQueue(sendSms);

				logger.info(LoggingUtils.objToStringIgnoreEx(sendSms));

			}
		} else if (Constants.SDP_MO.HDSD.equalsIgnoreCase(message)) {
			
		} else if (Constants.SDP_MO.GIA.equalsIgnoreCase(message)) {
			
		}

		ResponseDto resp = new ResponseDto();
		resp.setDescription("Success");
		resp.setStatus("0");
		return resp;
	}
}
