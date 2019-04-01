package com.baominh.sdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="mtqueue")
@NamedQuery(name="Mtqueue.findAll", query="SELECT m from Mtqueue m")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mtqueue implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "isdn")
    private String isdn;

    @Column(name = "serviceAddress")
    private String serviceAddress;

    @Column(name = "content")
    private String content;

    @Column(name = "serviceId")
    private Integer serviceId;

    @Column(name = "description")
    private String description;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "status")
    private Integer status;

}
