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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prize_type")
@Getter
@Setter
@NoArgsConstructor
public class PrizeType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String prizeName;
	@Column(columnDefinition = "text")
	String prizeDesc;
	@Column(columnDefinition = "text")
	String image;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			fetch = FetchType.EAGER, mappedBy = "prizeType")
	List<PrizePointValue> pointValues = new ArrayList<>();

	public PrizeType(Long id) {
		this.id = id;
	}
}
