package com.net.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lead_offer")
@Data
public class LeadOffer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long userId;
	String sourceNetwork;

	String campId;
	String campName;
	String sid;
	String sid2;
	String sid3;
	String status;
	String commission;
	String ip;
	String reversalReason;
	String test;
	String vcValue;
	String leadID;
	String country;
}
