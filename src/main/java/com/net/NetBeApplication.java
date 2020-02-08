package com.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NetBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetBeApplication.class, args);
	}

}
