package com.net.dto.prize;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrizeTypeDTO {
	Long id;
	String prizeName;
	String prizeDesc;
	String image;

	List<PrizePointValueDTO> pointValues = new ArrayList<>();
}
