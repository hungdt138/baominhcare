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
 * The persistent class for the schedulelist database table.
 * 
 */
@Entity
@NamedQuery(name = "Schedulelist.findAll", query = "SELECT s FROM Schedulelist s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedulelist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer scheduleID;

	private Integer serviceID;

	

}