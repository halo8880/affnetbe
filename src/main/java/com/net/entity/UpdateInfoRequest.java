package com.net.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "update_info_request")
@Getter
@Setter
public class UpdateInfoRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String newInfo;
	String confirmCode;

	@Enumerated(EnumType.STRING)
	Status status;
	@Enumerated(EnumType.STRING)
	InfoType infoType;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

	public enum Status {
		WAITING,
		PROCESSED
	}

	public enum InfoType {
		PAYPAL
	}
}
