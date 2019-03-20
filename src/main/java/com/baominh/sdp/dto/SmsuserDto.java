/**
 * 
 */
package com.baominh.sdp.dto;

import javax.persistence.Column;

import com.baominh.sdp.entity.Smsuser;
import com.baominh.sdp.entity.Smsuser.SmsuserBuilder;

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
public class SmsuserDto {
	private Integer id;

	private Integer active;

	private Integer cancelby;

	private int chargeFail;

	private String lastCharge;

	private String phone;

	private String registerDate;

	private String registerType;

	private Integer serviceID;

	private String unregisterDate;
}
