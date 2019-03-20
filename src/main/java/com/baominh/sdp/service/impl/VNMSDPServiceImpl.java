/**
 * 
 */
package com.baominh.sdp.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTResponseDto;
import com.baominh.sdp.exception.BMException;
import com.baominh.sdp.exception.ErrorCode;
import com.baominh.sdp.repository.jpa.ServiceRepository;
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

	@Value("${sdp.mtapi.url}")
	private String sdpurl;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ServiceRepository serviceRepository;

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
			throw new BMException(ErrorCode.SEND_MT_ERROR,
					new StringBuilder(" parameter: ").append(LoggingUtils.objToStringIgnoreEx(mtRequest))
							.append(" trace message.").append(e.getMessage()));
		} catch (JsonMappingException e) {
			throw new BMException(ErrorCode.SEND_MT_ERROR,
					new StringBuilder(" parameter: ").append(LoggingUtils.objToStringIgnoreEx(mtRequest))
							.append(" trace message.").append(e.getMessage()));
		} catch (IOException e) {
			throw new BMException(ErrorCode.SEND_MT_ERROR,
					new StringBuilder(" parameter: ").append(LoggingUtils.objToStringIgnoreEx(mtRequest))
							.append(" trace message.").append(e.getMessage()));
		}

		return mtResp;
	}
	
	

}
