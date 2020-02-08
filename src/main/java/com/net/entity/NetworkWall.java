package com.net.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "network_wall")
@Data
public class NetworkWall {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String networkName;
	@Column(columnDefinition = "text")
	String image;
	String iframeUrl;
}
