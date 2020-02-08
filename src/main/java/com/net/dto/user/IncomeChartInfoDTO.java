package com.net.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class IncomeChartInfoDTO {
	@DateTimeFormat(pattern = "dd-MM")
	@JsonFormat(pattern = "dd-MM")
	LocalDate time;
	Integer numberOfLeads;
}
