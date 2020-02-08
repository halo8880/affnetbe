package com.net.dto.user;

import lombok.Data;

@Data
public class SignUpUserDTO {
	private String username;
	private String password;
	private String paypal;
	private String refBy;
}
