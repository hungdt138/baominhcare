package com.baominh.sdp.exception;
public interface IErrorCode {
	
	String  getErrorCode();
	String  getMessageCode();
	Integer  getHttpStatus();
}
