/**
 * 
 */
package com.baominh.sdp.dto;

import javax.persistence.Column;

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
public class ServiceDto {
	private Integer id;
	private Integer chargingID;

	private String contentProvider;

	private String description;

	private String disableMsg;

	private Integer enable;

	private String failRegisterMsg;

	private String failUnregisterMsg;

	private Integer groupID;

	private String name;

	private Integer scheduleID;

	private String sendType;

	private String successRegisterMsg;

	private String successUnregisterMsg;

	private String syntaxRegister;

	private String syntaxUnregister;

	private Integer touchtype;

	private Integer waitForContent;

	private Integer sdpProductId;

	@Column(name = "sdp_category_id")
	private Integer sdpCategoryId;
}
