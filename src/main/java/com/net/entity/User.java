package com.net.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	private String username;
	private String password;
	private String paypal;
	private Boolean enabled = true;
	private String confirmToken;
	private Integer point = 0;
	private Integer refPoint = 0;
	private Integer spentPoint = 0;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, mappedBy = "user")
	List<PrizeRequest> prizeRequests = new ArrayList<>();

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, mappedBy = "user")
	List<UpdateInfoRequest> updateInfoRequests = new ArrayList<>();

	public User(Long id) {
		this.id = id;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
