package com.net.controller;

import com.net.entity.LeadOffer;
import com.net.repository.LeadOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/cpagrip")
public class CPAGripController {
	@Autowired
	LeadOfferRepository leadOfferRepository;

	@PostMapping("/postback")
	public void cpaleadPostbackReceiver(
			@RequestParam(name = "payout", required = false) String payout,
			@RequestParam(name = "offer_id", required = false) String offer_id,
			@RequestParam(name = "tracking_id", required = false) String tracking_id,
			@RequestParam(name = "click_id", required = false) String click_id
	) {
		Long userId = 0L;
		try {
			userId = Long.parseLong(tracking_id);
		} catch (Exception e) {
			System.out.println(e);
		}
		LeadOffer leadOffer = new LeadOffer();
		leadOffer.setSourceNetwork("CPAGRIP");
		leadOffer.setCampId(offer_id);
		leadOffer.setSid(tracking_id);
		leadOffer.setCommission(payout);
		leadOffer.setLeadID(click_id);

		leadOffer.setLeadDate(LocalDate.now(ZoneOffset.UTC));
		leadOffer.setUserId(userId);
		leadOfferRepository.save(leadOffer);
	}
}
