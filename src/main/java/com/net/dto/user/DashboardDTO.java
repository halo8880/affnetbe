package com.net.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class DashboardDTO {
	UserDTO userInfo;
	List<IncomeChartInfoDTO> incomeChartInfos;
	List<DashboardLeadOfferDTO> top10LatestLeadOffers;
	Integer totalLeadThisMonth;
}
