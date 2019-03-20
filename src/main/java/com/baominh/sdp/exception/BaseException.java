package com.baominh.sdp.exception;

import java.util.ArrayList;
import java.util.List;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -7030732749416640448L;
	protected String message;
	protected Integer httpStatus;
	protected IErrorCode errorCode;
	protected String traceMessage;
	private List<BMFieldError> fieldErrors;

	public BaseException() {

		fieldErrors = new ArrayList<BMFieldError>();
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public IErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(IErrorCode errorCode) {
		this.errorCode = errorCode;
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

	public List<BMFieldError> addFieldError(String fieldId, String errorCode, String errorMessage) {

		BMFieldError fieldError = new BMFieldError();
		fieldError.setFieldId(fieldId);
		fieldError.setErrorCode(errorCode);
		fieldError.setErrorMessage(errorMessage);

		fieldErrors.add(fieldError);

		return fieldErrors;
	}

	public List<BMFieldError> addFieldError(String fieldId, String errorCode, String errorMessage,
			Object[] errorMessageArgs) {

		BMFieldError fieldError = new BMFieldError();
		fieldError.setFieldId(fieldId);
		fieldError.setErrorCode(errorCode);
		fieldError.setErrorMessage(errorMessage);
		fieldError.setErrorMessageArgs(errorMessageArgs);

		fieldErrors.add(fieldError);

		return fieldErrors;
	}

	public BMRestError transformToRestError() {

		BMRestError restError = new BMRestError();

		restError.setErrorCode(errorCode.getErrorCode());
		restError.setTraceMessage(traceMessage);

		restError.setMessage(errorCode.getMessageCode());
		restError.setFieldErrors(fieldErrors);
		restError.setException(this.getClass().getName());
		return restError;
	}

}
