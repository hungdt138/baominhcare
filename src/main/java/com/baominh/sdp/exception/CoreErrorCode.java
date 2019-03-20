package com.baominh.sdp.exception;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public enum CoreErrorCode implements IErrorCode {

	UNKNOWN_ERROR("UNKNOWN_ERROR","Lỗi không xác định"), 
	INVALID_PARAMS("INVALID_PARAMS","Lỗi tham số"),
	DATA_NOT_FOUND("404","Không tìm thấy dữ liệu",HttpServletResponse.SC_NOT_FOUND), 
	FORM_ERROR("FORM_ERROR","Thông tin nhập liệu sai"),
	REST_ERROR("REST_ERROR","Webservice error"),
	SAVE_FILE_ERROR("SAVE_FILE_ERROR","Lưu file không thành công"),
	INVOKE_METHOD_ERROR("2011","Lỗi gọi hàm callback từ RabbitMQ"), 
	EVENTID_NOT_VALID("2012","EventId không hợp lệ"), 
	EVENT_BUSINESS_ERROR("2013","Bond Event,Nghiệp vụ không hợp lệ"),
	BOND_EVENT_INVALID_PRECONDITION("5047","Không thỏa mãn điều kiện gửi event tới rabbitmq, listedBond:{0}, status:{1}",HttpServletResponse.SC_BAD_REQUEST),
	LOAD_PRIVATE_KEY_ERROR("5049","Lỗi load RSA private key",HttpServletResponse.SC_BAD_REQUEST),
	LOAD_PUBLIC_KEY_ERROR("5050","Lỗi load RSA private key",HttpServletResponse.SC_BAD_REQUEST),
	GENERATE_KEYPAIR_ERROR("5051","Lỗi generate RSA keypair",HttpServletResponse.SC_BAD_REQUEST), 
	LOAD_KEYPAIR_ERROR("5052","Lỗi load RSA keypair",HttpServletResponse.SC_BAD_REQUEST), 
	SAVE_KEYPAIR_ERROR("5053","Lỗi lưu keypair",HttpServletResponse.SC_BAD_REQUEST), 
	RSA_ENCRYPT_ERROR("5054","Encrypt rsa message has error",HttpServletResponse.SC_BAD_REQUEST), 
	RSA_DECRYPT_ERROR("5055","Decrypt rsa message has error",HttpServletResponse.SC_BAD_REQUEST), 
	;

	// lookup table to be used to find enum for conversion
	private static final Map<String, CoreErrorCode> lookup = new HashMap<String, CoreErrorCode>();

	static {
		for (CoreErrorCode e : EnumSet.allOf(CoreErrorCode.class))
			lookup.put(e.getErrorCode(), e);
	}

	private String errorCode;

	private String messageCode;
	private Integer httpStatus;

	

	CoreErrorCode(String errorCode, String messageCode) {
		this.errorCode = errorCode;
		this.messageCode = messageCode;
		this.httpStatus = HttpServletResponse.SC_BAD_REQUEST;
	}
	CoreErrorCode(String errorCode, String messageCode,Integer httpStatus) {
		this.errorCode = errorCode;
		this.messageCode = messageCode;
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public static CoreErrorCode get(String errorCode) {
		return lookup.get(errorCode);
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getMessageCode() {
		return messageCode;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}


}
