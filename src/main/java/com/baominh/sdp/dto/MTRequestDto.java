/**
 * 
 */
package com.baominh.sdp.dto;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HungDT
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MTRequestDto {
	private String transId;
	private String username;
	private String password;
	private String time;
	private String isdn;
	private String serviceAddress;
	private String categoryId;
	private String productId;
	private String moid;
	private String message;
	private Integer unicode;
	private Integer flash;
	private String href;
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> toHashMap() {
		return new ObjectMapper().convertValue(this, HashMap.class);
	}

}
