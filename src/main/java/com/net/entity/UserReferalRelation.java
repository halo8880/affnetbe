package com.net.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_referal_relation")
@Getter
@Setter
@NoArgsConstructor
public class UserReferalRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long upperUserId;
	Long lowerUserId;
	Integer percent;
}
