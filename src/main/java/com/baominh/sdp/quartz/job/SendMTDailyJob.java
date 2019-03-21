/**
 * 
 */
package com.baominh.sdp.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baominh.sdp.service.VNMSDPService;

/**
 * @author HungDT
 *
 */
public class SendMTDailyJob implements Job {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VNMSDPService vnmSdpService;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		vnmSdpService.prepareForSendMT();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			logger.error("Error while SendMTDailyJob job", e);
		} finally {
			logger.info("SendMTDailyJob has finished...");
		}
	}
}
