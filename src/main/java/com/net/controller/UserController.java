package com.net.controller;

import com.net.entity.User;
import com.net.repository.UserRepository;
import com.net.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	UserRepository userRepository;

	@GetMapping("current")
	public String loggedUser() throws Exception {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<User> user = userRepository.findByUsername(currentUsername);
		if (!user.isPresent()) {
			throw new Exception("user not logged in");
		}
		return user.get().getId() + "";
	}
}
