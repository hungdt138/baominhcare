/**
 * 
 */
package com.baominh.sdp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hungdt8
 *
 */
public class LoggingUtils {
	public static String objToStringIgnoreEx(Object prm) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(prm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
