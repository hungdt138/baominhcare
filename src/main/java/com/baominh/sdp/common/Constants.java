/**
 * 
 */
package com.baominh.sdp.common;

/**
 * @author hungdt8
 *
 */
public class Constants {
	public interface SDP_COMMAND {
		public static final String REGISTER = "register";
		public static final String SUBSCRIPTION = "subscription";
		public static final String UNREGISTER = "unregister";
		public static final String CANCEL = "cancel";
	}
	
	public interface SDP_SEND_MT_RESPONSE_CODE {
		public static final String SUCCESS = "0";
		public static final String ERROR = "1";
	}
}
