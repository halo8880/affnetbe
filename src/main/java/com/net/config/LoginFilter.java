package com.net.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.net.dto.user.UserCredentials;
import com.net.repository.UserRepository;
import com.net.service.AuthenticationService;
import com.net.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private UserService userService;

	public LoginFilter(String url,
					   AuthenticationManager authManager,
					   UserRepository userRepository,
					   UserService userService,
					   PasswordEncoder passwordEncoder) {
		super(new AntPathRequestMatcher(url));
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException {
		UserCredentials userCredentials = new ObjectMapper()
				.readValue(req.getInputStream(), UserCredentials.class);
		if (!userRepository.existsByUsername(userCredentials.getUsername())) {
			userCredentials.setPassword("123");
			try {
				userService.signUp(userCredentials);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						userCredentials.getUsername(),
//						userCredentials.getPassword(),
						"123",
						Collections.emptyList()
				)
		);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req,
			HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException {
		AuthenticationService.addJWTToken(res, auth.getName());
	}
}
