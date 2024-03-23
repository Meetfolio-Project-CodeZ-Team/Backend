package com.codez4.meetfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MeetfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetfolioApplication.class, args);
	}

}
