package com.baominh.sdp.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.baominh.sdp.entity.Smslog.SmslogBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the settings database table.
 * 
 */
@Entity
@Table(name="settings")
@NamedQuery(name="Setting.findAll", query="SELECT s FROM Setting s")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="api_register")
	private byte apiRegister;

	@Column(name="hdsd_msg")
	private String hdsdMsg;

	@Column(name="huy_tbdv_fail_msg")
	private String huyTbdvFailMsg;

	@Column(name="huy_tbdv_success_msg")
	private String huyTbdvSuccessMsg;

	@Column(name="ktdv_fail_msg")
	private String ktdvFailMsg;

	@Column(name="ktdv_success_msg")
	private String ktdvSuccessMsg;

	@Column(name="password_expire")
	private int passwordExpire;

	@Column(name="password_fail_msg")
	private String passwordFailMsg;

	@Column(name="password_msg")
	private String passwordMsg;

	@Column(name="register_confirm")
	private byte registerConfirm;

	@Column(name="service_num")
	private int serviceNum;

	@Column(name="system_busy_msg")
	private String systemBusyMsg;

	@Column(name="wrong_syntax_msg")
	private String wrongSyntaxMsg;

	

}