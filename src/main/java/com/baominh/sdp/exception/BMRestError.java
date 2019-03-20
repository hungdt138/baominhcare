package com.baominh.sdp.exception;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BMRestError  implements Serializable{

	private static final long serialVersionUID = 7853373628294414978L;
	private String status = "error";
	private String errorCode;
	private String code;
	private String message;
	protected String traceMessage;
	private Long timestamp = System.currentTimeMillis();
	private String exception;
	private List<BMFieldError> fieldErrors ;

	

	public BMRestError() {
		super();
		fieldErrors = new ArrayList<BMFieldError>();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTraceMessage() {
		return traceMessage;
	}

	public void setTraceMessage(String traceMessage) {
		this.traceMessage = traceMessage;
	}

	public List<BMFieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<BMFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
	

}
