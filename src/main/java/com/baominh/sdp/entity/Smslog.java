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
 * The persistent class for the smslog database table.
 * 
 */
@Entity
@Table(name="smslog")
@NamedQuery(name = "Smslog.findAll", query = "SELECT s FROM Smslog s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Smslog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String content;

	private String date;
	
	@Column(name = "`from`")
	private String from;

	@Column(name = "retry_number")
	private Integer retryNumber;

	private Integer serviceID;

	@Column(name = "`status`")
	private String status;

	@Column(name = "`to`")
	private String to;

	private String type;

	

}