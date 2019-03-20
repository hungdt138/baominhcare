/**
 * 
 */
package com.baominh.sdp.dto;

import com.baominh.sdp.entity.Content;
import com.baominh.sdp.entity.Content.ContentBuilder;

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
public class ContentDto {
	private Integer id;

	private String content;

	private String dateToSend;

	private String importDate;

	private Integer isSend;

	private String lastModifyDate;

	private String sendDate;

	private Integer serviceID;
}
