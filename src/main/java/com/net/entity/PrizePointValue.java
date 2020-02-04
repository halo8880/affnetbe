package com.net.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prize_point_value")
@Getter
@Setter
@NoArgsConstructor
public class PrizePointValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Integer point;
	String value;
	@ManyToOne
	@JoinColumn(name = "prize_type_id")
	PrizeType prizeType;

	public PrizePointValue(Long id) {
		this.id = id;
	}
}
