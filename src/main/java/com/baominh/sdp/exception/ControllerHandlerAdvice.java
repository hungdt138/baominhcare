package com.baominh.sdp.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baominh.sdp.utils.LoggingUtils;

/**
 * catching TcbsException, other exception will be caught by default exception
 * handler
 * 
 * @author SangNV3
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerHandlerAdvice {
	private static Logger logger = LoggerFactory.getLogger(ControllerHandlerAdvice.class);

	@ExceptionHandler(BMException.class)
	public @ResponseBody BMRestError handleCustomException(BMException tcbsex, HttpServletRequest request,
			HttpServletResponse response) {

		logger.error("ExceptionHandelerAdvice:handle controller exception:{}<=>{}<=>{}", tcbsex.getErrorCode(),
				tcbsex.getMessage(), tcbsex.getTraceMessage());

		logger.error(tcbsex.getMessage(), tcbsex);
		logger.error(LoggingUtils.objToStringIgnoreEx(tcbsex.transformToRestError()));

		response.setStatus(tcbsex.getHttpStatus());

		BMRestError restError = tcbsex.transformToRestError();
//		TcbsAPIException rtv = new TcbsAPIException();
//		rtv.setCode(restError.getErrorCode());
//		rtv.setMessage(restError.getMessage());
		return restError;

	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody BMRestError handleCustomException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {

		logger.error("ExceptionHandelerAdvice:handle controller exception:{}", ex.getMessage());

		logger.error(ex.getMessage(), ex);

		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

//		TcbsAPIException rtv = new TcbsAPIException();
//		rtv.setCode(TcbsCoreErrorCode.UNKNOWN_ERROR.getErrorCode());
//		rtv.setMessage(TcbsCoreErrorCode.UNKNOWN_ERROR.getMessageCode());
		BMRestError restError = new BMRestError();
		restError.setCode(CoreErrorCode.UNKNOWN_ERROR.getErrorCode());
		restError.setMessage(ex.getMessage());
		restError.setException(ex.getClass().getName());
		return restError;

	}
}