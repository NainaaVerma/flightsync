package com.flightsync.flightsync;

import com.flightsync.flightsync.service.MockPriceGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsyncApplication.class, args);

		MockPriceGenerator generator = new MockPriceGenerator();
		int price = generator.generatePrice("Mumbai", "Delhi");
		System.out.println("Mumbai -> Delhi price: ₹" + price);
	}

}