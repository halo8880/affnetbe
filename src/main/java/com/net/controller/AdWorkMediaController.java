package com.net.controller;

import com.net.dto.AdWorkMediaResponseDTO;
import com.net.service.AdWorkMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("adworkmedia")
public class AdWorkMediaController {
	@Autowired
	AdWorkMediaService adWorkMediaService;

	@GetMapping
	public List<AdWorkMediaResponseDTO> getOffers() throws IOException {
		return adWorkMediaService.getOffers();
	}
}
