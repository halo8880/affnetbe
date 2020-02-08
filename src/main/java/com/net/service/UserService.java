package com.net.service;

import com.net.dto.user.SignUpUserDTO;
import com.net.dto.user.UserCredentials;
import com.net.entity.User;
import com.net.entity.UserReferalRelation;
import com.net.mapper.UserMapper;
import com.net.repository.UserReferalRelationRepository;
import com.net.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Log4j2
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserReferalRelationRepository userReferalRelationRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserMapper userMapper;

	public User getLoggedInUser() throws Exception {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<User> user = userRepository.findByUsername(currentUsername);
		if (!user.isPresent()) {
			throw new Exception("user not logged in");
		}
		return user.get();
	}

	public User signUp(UserCredentials user) throws Exception {
		Long refById = 0L;

		try {
			refById = Long.parseLong(user.getRefBy());
		} catch (Exception e) {
			log.warn("refBy is not a number: " + user.getRefBy());
		}
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new Exception("Email already exists");
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = userRepository.save(userMapper.userCredentialstoUser(user));
			if (!StringUtils.isEmpty(user.getRefBy()) && userRepository.existsById(refById)) {
				UserReferalRelation refRelation = new UserReferalRelation();
				refRelation.setPercent(10);
				refRelation.setUpperUserId(refById);
				refRelation.setUpperUserId(savedUser.getId());
				userReferalRelationRepository.save(refRelation);
			}
			return savedUser;
		}
	}
}
