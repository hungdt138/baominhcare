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
 * The persistent class for the content database table.
 * 
 */
@Entity
@NamedQuery(name = "Content.findAll", query = "SELECT c FROM Content c")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "content")
	private String content;

	@Column(name = "date_to_send")
	private String dateToSend;

	@Column(name = "import_date")
	private String importDate;

	@Column(name = "is_send")
	private Integer isSend;

	@Column(name = "last_modify_date")
	private String lastModifyDate;

	@Column(name = "send_date")
	private String sendDate;

	@Column(name = "serviceID")
	private Integer serviceID;

	

}