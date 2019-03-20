/**
 * 
 */
package com.baominh.sdp.dto;

import com.baominh.sdp.entity.Smslog;
import com.baominh.sdp.entity.Smslog.SmslogBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungdt8
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmslogDto {
	private Integer id;

	private String content;

	private String date;

	private String from;

	private Integer retryNumber;

	private Integer serviceID;

	private String status;

	private String to;

	private String type;
}
