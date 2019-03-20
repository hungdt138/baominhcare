/**
 * 
 */
package com.baominh.sdp.exception;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hungdt8
 *
 */
public enum ErrorCode implements IErrorCode {

	UNKNOWN_ERROR("UNKNOWN_ERROR", "Lỗi không xác định"), 
	INVALID_PARAMS("INVALID_PARAMS","Lỗi tham số"), 
	DATA_NOT_FOUND("404", "Không tìm thấy dữ liệu", HttpServletResponse.SC_NOT_FOUND), 
	FORM_ERROR("FORM_ERROR", "Thông tin nhập liệu sai"), 
	REST_ERROR("REST_ERROR", "Webservice error"), 
	SAVE_FILE_ERROR("SAVE_FILE_ERROR", "Lưu file không thành công"), 
	INVALID_WORKING_DAY("2007","Thời gian giao dịch không hợp lệ"), 
	PRODUCT_LOCKED("2008","Sản phẩm đang khóa"), 
	DUPLICATE_TRADING_CODE("2009","Mã hợp đồng đã tồn tại trong hệ thống"), 
	PRICING_ERROR("2010", "Lỗi Pricing"),
	// FIXME: add more here
	RECIEVE_ORDER_FROM_FLEX_ERROR("1001408", "Lỗi nhận trạng thái từ Flex về"),
	INVALID_CUSTOMER("INVALID_CUSTOMER", "TcbsId không tồn tại trong hệ thống"),
	INVALID_DATE_UNIT("INVALID_DATE_UNIT", "Invalid date unit"),
	TARGETSALE_NOT_CONFIG("TARGETSALE_NOT_CONFIG", "TargetSale not config yet!"),
	PARSE_DATA_ERROR("PARSE_DATA_ERROR", "Parse data error!"),
	BOND_PRODUCT_NOT_FOUND("BOND_PRODUCT_NOT_FOUND", "Bond product not found"),
	SEND_MT_ERROR("001","Error send mt to SDP VNM");

	// lookup table to be used to find enum for conversion
	private static final Map<String, ErrorCode> lookup = new HashMap<String, ErrorCode>();

	static {
		for (ErrorCode e : EnumSet.allOf(ErrorCode.class))
			lookup.put(e.getCode(), e);
	}

	private String code;

	private String messageCode;
	private Integer httpStatus;

	ErrorCode(String errorCode, String messageCode) {
		this.code = errorCode;
		this.messageCode = messageCode;
		this.httpStatus = HttpServletResponse.SC_BAD_REQUEST;
	}

	ErrorCode(String errorCode, String messageCode, Integer httpStatus) {
		this.code = errorCode;
		this.messageCode = messageCode;
		this.httpStatus = httpStatus;
	}

	
	public static ErrorCode get(String errorCode) {
		return lookup.get(errorCode);
	}

	public void setCode(String errorCode) {
		this.code = errorCode;
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
		return this.code;
	}

}
