package com.baominh.sdp.exception;

import java.text.MessageFormat;
import java.util.List;

public class BMException extends BaseException {

	private static final long serialVersionUID = 8823356956725033191L;

	public BMException(Throwable cause, IErrorCode errorCode) {
		super(errorCode.getCode() + "-" + (null == cause ? errorCode.getMessageCode() : cause.getMessage()),
				cause);
		this.setErrorCode(errorCode);
		this.setHttpStatus(errorCode.getHttpStatus());
		this.setMessageCode(errorCode.getMessageCode());
		if (cause != null) {
			this.setTraceMessage(cause.getMessage());
		}

	}

	public BMException(Throwable cause, IErrorCode errorCode, StringBuilder traceMessage) {
		super((new StringBuilder(errorCode.getCode())).append("-").append(traceMessage).toString(), cause);
		this.setErrorCode(errorCode);
		this.setTraceMessage(traceMessage.toString());
		this.setHttpStatus(errorCode.getHttpStatus());
		this.setMessageCode(errorCode.getMessageCode());
	}

	public BMException(Throwable cause, IErrorCode errorCode, StringBuilder traceMessage, Object... arguments) {
		super((new StringBuilder(errorCode.getCode())).append("-").append(traceMessage).toString(), cause);
		this.setErrorCode(errorCode);
		this.setTraceMessage(traceMessage.toString());
		this.setHttpStatus(errorCode.getHttpStatus());
		this.setMessageCode(this.messageCode);
		this.setArguments(arguments);
	}

	public BMException(Throwable cause, IErrorCode errorCode, Object... arguments) {
		super(errorCode.getCode() + "-" + (null == cause ? errorCode.getMessageCode() : cause.getMessage()),
				cause);
		this.setErrorCode(errorCode);
		this.setHttpStatus(errorCode.getHttpStatus());
		this.setMessageCode(this.messageCode);
		this.setArguments(arguments);
		if (cause != null) {
			this.setTraceMessage(cause.getMessage());
		}

	}

	public BMException(Throwable cause, IErrorCode errorCode, List<BMFieldError> fieldErrors) {
		super(errorCode.getCode() + "-" + (null == cause ? errorCode.getMessageCode() : cause.getMessage()),
				cause);
		this.setErrorCode(errorCode);
		this.setFieldErrors(fieldErrors);
		this.setHttpStatus(errorCode.getHttpStatus());
		this.setMessageCode(errorCode.getMessageCode());
		if (cause != null) {
			this.setTraceMessage(cause.getMessage());
		}

	}

	public BMException(Throwable cause, IErrorCode errorCode, List<BMFieldError> fieldErrors, StringBuilder traceMessage) {
	        super((new StringBuilder(errorCode.getCode())).append("-").append(traceMessage).toString(), cause);
	        this.setErrorCode(errorCode);
	        this.setFieldErrors(fieldErrors);
	        this.setTraceMessage(traceMessage.toString());
	        this.setHttpStatus(errorCode.getHttpStatus());
	        this.setMessageCode(errorCode.getMessageCode());
	        this.setArguments(this.arguments);
	    }

	public BMException(final BMRestError restError, final int httpStatus) {
		super();
		this.setErrorCode(new IErrorCode() {

			@Override
			public String getMessageCode() {
				return restError.getMessage();
			}

			@Override
			public Integer getHttpStatus() {
				return httpStatus;
			}

			@Override
			public String getCode() {
				return restError.getErrorCode();
			}
		});
		this.setFieldErrors(restError.getFieldErrors());
		this.setTraceMessage(restError.getTraceMessage());
	}

}
