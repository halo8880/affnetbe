package com.net.dto.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DashboardLeadOfferDTO {
	@DateTimeFormat(pattern = "dd MMM, yyyy")
	LocalDate leadDate;
	String campaignName;
	Integer point;
}
