package com.net.dto.adwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdWorkMediaPostbackRequestDTO {
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
