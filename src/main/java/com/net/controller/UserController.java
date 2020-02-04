package com.net.controller;

import com.net.dto.UserDTO;
import com.net.entity.UpdateInfoRequest;
import com.net.entity.User;
import com.net.mapper.UserMapper;
import com.net.repository.UpdateInfoRequestRepository;
import com.net.repository.UserRepository;
import com.net.service.UserDetailsServiceImpl;
import com.net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserMapper userMapper;
	@Autowired
	UpdateInfoRequestRepository updateInfoRequestRepository;

	@GetMapping("/user/current")
	public UserDTO loggedUser() throws Exception {
		User user = userService.getLoggedInUser();
		return userMapper.userToUserDTO(user);
	}

	@PostMapping("signup")
	public String signup(@RequestBody User user) throws Exception {

		if (userRepository.existsByUsername(user.getUsername())) {
			throw new Exception("Email already exists");
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return user.getUsername();
		}
	}

	@GetMapping("/user/confirm")
	public void confirmUpdateInfo(@RequestParam("confirmCode") String confirmCode) throws Exception {
		Optional<UpdateInfoRequest> updateRequest = updateInfoRequestRepository.findFirstByConfirmCodeAndStatus(confirmCode, UpdateInfoRequest.Status.WAITING);
		if (updateRequest.isPresent()) {
			if (updateRequest.get().getInfoType() == UpdateInfoRequest.InfoType.PAYPAL) {
				User user = updateRequest.get().getUser();
				user.setPaypal(updateRequest.get().getNewInfo());
				userRepository.save(user);
				updateRequest.get().setStatus(UpdateInfoRequest.Status.PROCESSED);
				updateInfoRequestRepository.save(updateRequest.get());
			}
		} else {
			throw new Exception("Request does not exists");
		}
	}

	@PostMapping("/user/update-paypal")
	public void updatePaypalInfo(@RequestBody Map<String, String> updatedInfo) throws Exception {
		String newPaypalInfo = updatedInfo.get("newPaypalInfo");
		UpdateInfoRequest updateRequest = new UpdateInfoRequest();
		updateRequest.setInfoType(UpdateInfoRequest.InfoType.PAYPAL);
		updateRequest.setNewInfo(newPaypalInfo);
		updateRequest.setStatus(UpdateInfoRequest.Status.WAITING);
		updateRequest.setUser(new User(userService.getLoggedInUser().getId()));
		updateRequest.setConfirmCode(LocalDateTime.now().getNano() + "");
		updateInfoRequestRepository.save(updateRequest);
		//TODO send mail with confirm code
	}
}
