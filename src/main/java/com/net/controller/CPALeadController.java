package com.net.controller;

import com.net.entity.LeadOffer;
import com.net.repository.LeadOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping("cpalead")
public class CPALeadController {
	@Autowired
	LeadOfferRepository leadOfferRepository;

	@GetMapping("/postback")
	public void cpaleadPostbackReceiver(
			@RequestParam(name = "campaign_id", required = false) String campaign_id,
			@RequestParam(name = "campaign_name", required = false) String campaign_name,
			@RequestParam(name = "subid", required = false) String subid,
			@RequestParam(name = "subid2", required = false) String subid2,
			@RequestParam(name = "subid3", required = false) String subid3,
			@RequestParam(name = "idfa", required = false) String idfa,
			@RequestParam(name = "gaid", required = false) String gaid,
			@RequestParam(name = "payout", required = false) String payout,
			@RequestParam(name = "ip_address", required = false) String ip_address,
			@RequestParam(name = "gateway_id", required = false) String gateway_id,
			@RequestParam(name = "lead_id", required = false) String lead_id,
			@RequestParam(name = "country_iso", required = false) String country_iso,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "virtual_currency", required = false) String virtual_currency
	) {
		LeadOffer leadOffer = new LeadOffer();
		leadOffer.setSourceNetwork("CPALEAD");
		leadOffer.setCampId(campaign_id);
		leadOffer.setCampName(campaign_name);
		leadOffer.setSid(subid);
		leadOffer.setSid2(subid2);
		leadOffer.setSid3(subid3);
		// todo idfa gaid password
		leadOffer.setCommission(payout);
		leadOffer.setIp(ip_address);
		leadOffer.setLeadID(lead_id);
		leadOffer.setCountry(country_iso);
		leadOffer.setVcValue(virtual_currency);
		leadOffer.setLeadDate(LocalDate.now(ZoneOffset.UTC));

		leadOfferRepository.save(leadOffer);
	}
}
