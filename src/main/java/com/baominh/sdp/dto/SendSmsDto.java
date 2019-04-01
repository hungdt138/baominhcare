/**
 * 
 */
package com.baominh.sdp.dto;

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
public class SendSmsDto {
	private String isdn;
	private String serviceAddress;
	private String content;
	private String moId;
	private Integer unicode;
	private Integer flash;
	private String href;
	private Integer serviceId;
}
