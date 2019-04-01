package com.baominh.sdp.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.baominh.sdp.dto.MTRequestDto;
import com.baominh.sdp.dto.MTRequestDto.MTRequestDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the smsuser database table.
 * 
 */
@Entity
@Table(name="smsuser")
@NamedQuery(name="Smsuser.findAll", query="SELECT s FROM Smsuser s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Smsuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer active;

	private Integer cancelby;

	@Column(name="charge_fail")
	private int chargeFail;

	@Column(name="last_charge")
	private String lastCharge;

	private String phone;

	@Column(name="register_date")
	private String registerDate;

	@Column(name="register_type")
	private String registerType;

	private Integer serviceID;

	@Column(name="unregister_date")
	private String unregisterDate;

	
}