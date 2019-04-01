package com.baominh.sdp.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.baominh.sdp.entity.Content.ContentBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the charging database table.
 * 
 */
@Entity
@Table(name="charging")
@NamedQuery(name="Charging.findAll", query="SELECT c FROM Charging c")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Charging implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer freetime;

	private String hour;

	private String name;

	private Integer price;

	private String type;

	

}