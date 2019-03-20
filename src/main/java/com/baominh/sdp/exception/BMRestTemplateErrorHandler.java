package com.baominh.sdp.exception;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BMRestTemplateErrorHandler implements ResponseErrorHandler{
	private static Logger logger = LoggerFactory.getLogger(BMRestTemplateErrorHandler.class);
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		HttpStatus status = response.getStatusCode();
		HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException  {
		InputStream ips = response.getBody();
		StringWriter writer = new StringWriter();
		IOUtils.copy(ips, writer, "UTF-8");
		String restErrorStr =  writer.toString();
		logger.info("restErrorStr: {}",restErrorStr);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			BMRestError restError = objectMapper.readValue(restErrorStr, BMRestError.class);
			HttpStatus status = response.getStatusCode();
			Integer httpStatus = status.value();
			throw new BMException(restError, httpStatus);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new BMException(e, CoreErrorCode.UNKNOWN_ERROR,response.getRawStatusCode(), e.getMessage());
		}
	}

}
