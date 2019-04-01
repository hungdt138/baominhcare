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
 * The persistent class for the schedule database table.
 * 
 */
@Entity
@Table(name="schedule")
@NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer day;

	private String hour;

	private String name;

	private Integer week;

	

}