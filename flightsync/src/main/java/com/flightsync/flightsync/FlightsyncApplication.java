package com.flightsync.flightsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.flightsync.flightsync.repository")
public class FlightsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsyncApplication.class, args);
	}
}