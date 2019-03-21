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
 * The persistent class for the services database table.
 * 
 */
@Entity
@Table(name = "services")
@NamedQuery(name = "ServiceEntity.findAll", query = "SELECT s FROM ServiceEntity s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer chargingID;

	@Column(name = "content_provider")
	private String contentProvider;

	private String description;

	@Column(name = "disable_msg")
	private String disableMsg;

	private Integer enable;

	@Column(name = "fail_register_msg")
	private String failRegisterMsg;

	@Column(name = "fail_unregister_msg")
	private String failUnregisterMsg;

	private Integer groupID;

	private String name;

	private Integer scheduleID;

	@Column(name = "send_type")
	private String sendType;

	@Column(name = "success_register_msg")
	private String successRegisterMsg;

	@Column(name = "success_unregister_msg")
	private String successUnregisterMsg;

	@Column(name = "syntax_register")
	private String syntaxRegister;

	@Column(name = "syntax_unregister")
	private String syntaxUnregister;

	private Integer touchtype;

	@Column(name = "wait_for_content")
	private Integer waitForContent;

	@Column(name = "sdp_product_id")
	private Integer sdpProductId;

	@Column(name = "sdp_category_id")
	private Integer sdpCategoryId;

	

}