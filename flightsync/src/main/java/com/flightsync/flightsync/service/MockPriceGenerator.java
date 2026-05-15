package com.flightsync.flightsync.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class MockPriceGenerator {

    private final Random random = new Random();

    public int generatePrice(String from, String to) {
        int basePrice = 5000;
        int hour = java.time.LocalTime.now().getHour();
        if (hour >= 8 && hour <= 10) {
            basePrice += 2000;
        }
        int fluctuation = random.nextInt(3000);

        return basePrice + fluctuation;
    }
}