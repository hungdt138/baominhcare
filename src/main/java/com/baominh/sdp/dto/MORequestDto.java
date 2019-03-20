/**
 * 
 */
package com.baominh.sdp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author hungdt8
 *
 */
@Data
@Builder
public class MORequestDto {
	private String id;
	private String transid;
	private String isdn;
	private String serviceaddress;
	private String productid;
	private String moid;
	private String amount;
	private String message;
	private String hash;
	private String categoryid;
}
