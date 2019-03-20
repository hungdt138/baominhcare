package com.baominh.sdp.exception;
import java.text.MessageFormat;
import java.util.List;


public class BMException extends BaseException {
	
	private static final long serialVersionUID = 8823356956725033191L;

	public BMException(IErrorCode tcbsErrorCode) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setHttpStatus(tcbsErrorCode.getHttpStatus());
		this.setMessage(tcbsErrorCode.getMessageCode());
	}
	public BMException(IErrorCode tcbsErrorCode,String traceMessage) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setTraceMessage(traceMessage);
		this.setHttpStatus(tcbsErrorCode.getHttpStatus());
		this.setMessage(tcbsErrorCode.getMessageCode());
	}
	public BMException(IErrorCode tcbsErrorCode,int httpStatus,String traceMessage) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setTraceMessage(traceMessage);
		this.setMessage(tcbsErrorCode.getMessageCode());
		this.setHttpStatus(httpStatus);
	}
	public BMException(IErrorCode tcbsErrorCode,String traceMessage,Object ... arguments) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setTraceMessage(traceMessage);
		this.setHttpStatus(tcbsErrorCode.getHttpStatus());
		String message = MessageFormat.format(tcbsErrorCode.getMessageCode(), arguments);
		this.setMessage(message);
	}
	public BMException(IErrorCode tcbsErrorCode, Object ... arguments) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setHttpStatus(tcbsErrorCode.getHttpStatus());
		String message = MessageFormat.format(tcbsErrorCode.getMessageCode(), arguments);
		this.setMessage(message);
	}
	
	public BMException(IErrorCode tcbsErrorCode,List<BMFieldError> fieldErrors) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setFieldErrors(fieldErrors);
		this.setHttpStatus(tcbsErrorCode.getHttpStatus());
		this.setMessage(tcbsErrorCode.getMessageCode());
	}
	public BMException(IErrorCode tcbsErrorCode,List<BMFieldError> fieldErrors,String traceMessage) {
		super();
		this.setErrorCode(tcbsErrorCode);
		this.setFieldErrors(fieldErrors);
		this.setTraceMessage(traceMessage);
		this.setHttpStatus(tcbsErrorCode.getHttpStatus());
		this.setMessage(tcbsErrorCode.getMessageCode());
	}
	
	public BMException(final BMRestError restError,final int httpStatus) {
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
			public String getErrorCode() {
				return restError.getErrorCode();
			}
		});
		this.setFieldErrors(restError.getFieldErrors());
		this.setTraceMessage(restError.getTraceMessage());
	}

}
