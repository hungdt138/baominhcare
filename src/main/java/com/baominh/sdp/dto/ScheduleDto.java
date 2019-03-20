/**
 * 
 */
package com.baominh.sdp.dto;

import com.baominh.sdp.entity.Schedule;
import com.baominh.sdp.entity.Schedule.ScheduleBuilder;

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
public class ScheduleDto {
	private Integer id;

	private Integer day;

	private String hour;

	private String name;

	private Integer week;
}
