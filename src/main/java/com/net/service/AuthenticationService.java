package com.net.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

public class AuthenticationService {
	private static final long EXPIRATIONTIME = 864_000_00;
	private static final String SIGNINGKEY = "signingKey";
	private static final String BEARER_PREFIX = "Bearer";

	static public void addJWTToken(HttpServletResponse response, String username) throws IOException {
		String JwtToken = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
				.compact();
		response.addHeader("Authorization", BEARER_PREFIX + " " + JwtToken);
		response.addHeader("content-type","application/json");
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.getWriter().println(
				"{\"token\": \"Bearer " + JwtToken + "\"}"
		);
	}

	static public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey(SIGNINGKEY)
					.parseClaimsJws(token.replace(BEARER_PREFIX, ""))
					.getBody()
					.getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, emptyList());
			} else {
				throw new RuntimeException("Authentication failed");
			}
		}
		return null;
	}
}
