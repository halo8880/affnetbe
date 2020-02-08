package com.net.config;

import com.net.repository.UserRepository;
import com.net.service.UserDetailsServiceImpl;
import com.net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl customUserDetailsService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/signup").permitAll()
				.antMatchers(HttpMethod.GET, "/user/confirm").permitAll()
				.antMatchers(HttpMethod.GET, "/static/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(new LoginFilter("/login", authenticationManager(), userRepository, userService, passwordEncoder()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new AuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		config.applyPermitDefaultValues();

		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
