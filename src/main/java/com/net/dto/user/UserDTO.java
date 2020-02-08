package com.net.dto.user;

import lombok.Data;

@Data
public class UserDTO {
	Long id;
	String username;
	String paypal;
	Integer point = 0;
	Integer refPoint = 0;
	Integer spentPoint = 0;
	Integer availablePoint = 0;

	public Integer getAvailablePoint() {
		return point + refPoint - spentPoint;
	}
}
