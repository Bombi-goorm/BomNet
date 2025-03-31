package com.bombi.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@EnableJpaAuditing
@SpringBootApplication
public class CoreApplication {

	@Value("${JWT_SECRET}")
	private String jwtSecret;

	@PostConstruct
	public void init() {
		System.out.println("JWT_SECRET = " + jwtSecret);
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
