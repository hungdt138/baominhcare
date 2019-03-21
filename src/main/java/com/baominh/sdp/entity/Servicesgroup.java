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
 * The persistent class for the servicesgroup database table.
 * 
 */
@Entity
@NamedQuery(name = "Servicesgroup.findAll", query = "SELECT s FROM Servicesgroup s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Servicesgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	

}