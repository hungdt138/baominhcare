package com.baominh.sdp.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baominh.sdp.service.VNMSDPService;

//@Component
public class GetMTJob implements Job {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VNMSDPService vnmSdpService;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			vnmSdpService.prepareForSendMT();
			Thread.sleep(10000);
			vnmSdpService.sendSMSDaily();
			Thread.sleep(20000);
			
		} catch (InterruptedException e) {
			logger.error("Error while executing GetMTJob", e);
		} finally {
			logger.info("GetMTJob has finished...");
		}
	}
}
