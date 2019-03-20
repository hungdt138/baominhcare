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
public class SubscriberRequestDto {
	private String id;
	private String transid;
	private String isdn;
	private String serviceaddress;
	private String productid;
	private String command;
	private String amount;
	private String expiration;
	private String hash;
	private String categoryid;
}
