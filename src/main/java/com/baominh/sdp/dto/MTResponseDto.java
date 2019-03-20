/**
 * 
 */
package com.baominh.sdp.dto;

import com.baominh.sdp.dto.MTRequestDto.MTRequestDtoBuilder;

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
public class MTResponseDto {
	private String username;
	private String transId;
	private Integer status;
	private String description;

	

}
