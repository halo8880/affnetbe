package com.net.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "offer")
@Getter
@Setter
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String campId;
	String campName;
	String campDesc;
	String money;
	String url;
	String image;
	String categories;
	String incent;
	String countries;
	String teaserText;
	String teaserDesc;
	String deviceType;

	String sourceNetwork;
}
