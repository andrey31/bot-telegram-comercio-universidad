package com.example.apoyoulatina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ApoyoulatinaApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(ApoyoulatinaApplication.class, args);
	}

}
