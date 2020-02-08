package com.net.dto.cpalead;

import lombok.Data;

@Data
public class CPALeadPostbackRequestDTO {
	String campaign_id;
	String campaign_name;
	String subid;
	String subid2;
	String subid3;
	String idfa;
	String gaid;
	String payout;
	String ip_address;
	String gateway_id;
	String lead_id;
	String country_iso;
	String password;
	String virtual_currency;
}
