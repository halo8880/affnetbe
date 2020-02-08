package com.net.service;

import com.net.dto.user.UserCredentials;
import com.net.entity.UpdateInfoRequest;
import com.net.entity.User;
import com.net.entity.UserReferalRelation;
import com.net.mapper.UserMapper;
import com.net.repository.UpdateInfoRequestRepository;
import com.net.repository.UserReferalRelationRepository;
import com.net.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class UserService {
	@Autowired
	EmailSenderService emailSenderService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserReferalRelationRepository userReferalRelationRepository;
	@Autowired
	UpdateInfoRequestRepository updateInfoRequestRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserMapper userMapper;

	public UserService() throws IOException {
		this.PAYPAL_CHANGE_TEMPLATE = IOUtils.toString(
				new ClassPathResource(PAYPAL_CHANGE_TEMPLATE_PATH)
						.getInputStream(), StandardCharsets.UTF_8.name());
	}

	private static final String PAYPAL_CHANGE_TEMPLATE_PATH = "templates/paypalChange.html";
	private final String PAYPAL_CHANGE_TEMPLATE;

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

	public void updatePaypal(@RequestBody Map<String, String> updatedInfo) throws Exception {
		String newPaypalInfo = updatedInfo.get("newPaypalInfo");
		UpdateInfoRequest updateRequest = new UpdateInfoRequest();
		updateRequest.setInfoType(UpdateInfoRequest.InfoType.PAYPAL);
		updateRequest.setNewInfo(newPaypalInfo);
		updateRequest.setStatus(UpdateInfoRequest.Status.WAITING);
		updateRequest.setUser(new User(getLoggedInUser().getId()));
		updateRequest.setConfirmCode(LocalDateTime.now().getNano() + "");
		updateInfoRequestRepository.save(updateRequest);
		emailSenderService.sendEmailUsingTo(Arrays.asList(getLoggedInUser().getUsername()),
				"Please confirm your Offrewards account change!",
				PAYPAL_CHANGE_TEMPLATE.replace("$OFFREWARDS_CONFIRMURL", "http://offrewards.com" + "/" + updateRequest.getConfirmCode()));
	}
}
