package com.baominh.sdp.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.baominh.sdp.entity.Content.ContentBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the charginglog database table.
 * 
 */
@Entity
@NamedQuery(name="Charginglog.findAll", query="SELECT c FROM Charginglog c")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Charginglog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="charge_time")
	private Integer chargeTime;

	private Integer chargingID;

	private String date;

	private String phone;

	private Integer price;

	private String reason;

	@Column(name="reason_code")
	private Integer reasonCode;

	private Integer serviceID;

	

}