package com.flightsync.flightsync;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"alert.emails=test@gmail.com",
		"alert.default-threshold=6000",
		"flight.routes=Mumbai->Delhi",
		"flight.check-interval-ms=5000",
		"spring.mail.username=test@gmail.com",
		"spring.mail.password=testpassword"
})
class FlightsyncApplicationTests {

	@Test
	void contextLoads() {
	}
}