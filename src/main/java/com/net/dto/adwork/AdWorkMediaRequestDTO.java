package com.net.dto.adwork;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AdWorkMediaRequestDTO {
	int pubID = 171665;
	String apiID = "qg4uytx9j0jss3yd5v70z1nmih8go32edws8m";
	String format = "json";
	boolean campDetails = true;
	String siteID;
	String userIP;
	String optimize;
	/**
	 * oType - Set the campaign type according to the values below:
	 * oType=1 - Signups/Downloads/Surveys/Submits Campaigns
	 * oType=2 - Signups/Downloads/Surveys/Submits & Mobile PIN/SMS Campaigns **Recommended for Lockers & Offer Walls**
	 * oType=3 - Only Mobile PIN/SMS Campaigns
	 * oType=4 - Mobiles PIN/SMS & Trial Campaigns
	 * oType=5 - Only CPI Campaigns (iOS and Android install offers)
	 */
	String oType;
	String gateway;
	/**
	 * You can also sort by the allowed incentive type using the incentType variable:
	 * incentType=1 (all incent types), incentType=2 (all points), incentType=3 (all cash incent)
	 */
	String incentType;
	String maxCampaigns;
	String minRate;
	String campID;
	String country_code;
	/**
	 * isMobile - Mobile-Friendly settings to target mobile devices:
	 * isMobile=1 - Target Mobile Friendly Offers (General Targeting)
	 * isMobile=2 - Target iOS Mobile Devices
	 * isMobile=3 - Target iOS Mobile Devices - iPhones
	 * isMobile=4 - Target iOS Mobile Devices - iPads
	 * isMobile=5 - Target All Android Mobile Devices
	 */
	String isMobile;

	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<>();
		map.put("pubID", this.pubID + "");
		map.put("apiID", this.apiID);
		map.put("format", this.format);
		map.put("campDetails", this.campDetails + "");
		map.put("maxCampaigns", this.maxCampaigns + "");

		return map;
	}
}
