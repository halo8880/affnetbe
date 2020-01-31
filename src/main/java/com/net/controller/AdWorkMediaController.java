package com.net.controller;

import com.net.dto.adwork.AdWorkMediaResponseDTO;
import com.net.entity.LeadOffer;
import com.net.repository.LeadOfferRepository;
import com.net.service.AdWorkMediaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("adworkmedia")
public class AdWorkMediaController {
	@Autowired
	AdWorkMediaService adWorkMediaService;

	@Autowired
	LeadOfferRepository leadOfferRepository;

	@GetMapping("offer")
	public List<AdWorkMediaResponseDTO> getOffers() throws IOException {
		return adWorkMediaService.getOffers();
	}

	@GetMapping("postback")
	public void postback(
			@RequestParam(name = "campaign_id", required = false) String campId,
			@RequestParam(name = "campaign_name", required = false) String campName,
			@RequestParam(name = "sid", required = false) String sid,
			@RequestParam(name = "sid2", required = false) String sid2,
			@RequestParam(name = "sid3", required = false) String sid3,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "commission", required = false) String commission,
			@RequestParam(name = "ip", required = false) String ip,
			@RequestParam(name = "reversal_reason", required = false) String reversalReason,
			@RequestParam(name = "test", required = false) String test,
			@RequestParam(name = "vc_value", required = false) String vcValue,
			@RequestParam(name = "leadID", required = false) String leadID,
			@RequestParam(name = "country", required = false) String country
	) {
//		AdWorkMediaPostbackRequestDTO requestDTO = new AdWorkMediaPostbackRequestDTO();
//		requestDTO.setCampId(campId);
//		requestDTO.setCampName(campName);
//		requestDTO.setSid(sid); //equal userId
//		requestDTO.setSid2(sid2);
//		requestDTO.setSid3(sid3);
//		requestDTO.setStatus(status);
//		requestDTO.setCommission(commission);
//		requestDTO.setIp(ip);
//		requestDTO.setReversalReason(reversalReason);
//		requestDTO.setTest(test);
//		requestDTO.setVcValue(vcValue);
//		requestDTO.setLeadID(leadID);
//		requestDTO.setCountry(country);

		LeadOffer leadOffer = new LeadOffer();
		leadOffer.setCampId(campId);
		leadOffer.setCampName(campName);
		leadOffer.setSid(sid); //equal userId
		leadOffer.setSid2(sid2);
		leadOffer.setSid3(sid3);
		leadOffer.setStatus(status);
		leadOffer.setCommission(commission);
		leadOffer.setIp(ip);
		leadOffer.setReversalReason(reversalReason);
		leadOffer.setTest(test);
		leadOffer.setVcValue(vcValue);
		leadOffer.setLeadID(leadID);
		leadOffer.setCountry(country);
		leadOffer.setUserId(Long.parseLong(sid));
		leadOffer.setSourceNetwork("ADWORKMEDIA");
		log.info(leadOffer);
		leadOfferRepository.save(leadOffer);
	}
}
