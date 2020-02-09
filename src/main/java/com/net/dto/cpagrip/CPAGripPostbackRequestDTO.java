package com.net.dto.cpagrip;

import lombok.Data;

@Data
public class CPAGripPostbackRequestDTO {
	String payout;
	String offer_id;
	String tracking_id;
	String click_id;
}
