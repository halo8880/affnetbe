package com.net.entity;

import com.net.enums.PrizeEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "prize_request")
@Getter
@Setter
public class PrizeRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	LocalDate requestDate;
	@Enumerated(EnumType.STRING)
	PrizeEnums.PrizeRequestStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prize_type_id")
	PrizeType prizeType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prize_point_value_id")
	PrizePointValue prizePointValue;

}
