package com.net.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StaticPageController {

	@GetMapping("/page/cpagrip")
	public String greeting2(
			@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			@RequestParam(name = "tracking_id", required = false, defaultValue = "") String userId,
							Model model) {
		model.addAttribute("name", name);
		model.addAttribute("userId", userId);
		return "cpagrip";
	}
}
