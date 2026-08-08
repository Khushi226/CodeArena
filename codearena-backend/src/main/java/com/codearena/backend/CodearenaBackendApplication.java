package com.codearena.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodearenaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodearenaBackendApplication.class, args);
		
	}
}
