package com.net.service;

import com.net.entity.User;
import com.net.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User getLoggedInUser() throws Exception {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<User> user = userRepository.findByUsername(currentUsername);
		if (!user.isPresent()) {
			throw new Exception("user not logged in");
		}
		return user.get();
	}
}
