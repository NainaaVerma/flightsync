package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Random;

@Slf4j
@Service
public class MockPriceGenerator {

    private static final int BASE_PRICE = 5000;
    private static final int PEAK_HOUR_SURGE = 2000;
    private static final int MAX_FLUCTUATION = 3000;
    private static final int PEAK_HOUR_START = 8;
    private static final int PEAK_HOUR_END = 10;

    private final Random random = new Random();

    public int generatePrice(String from, String to) {
        int price = BASE_PRICE + getPeakHourSurge() + getRandomFluctuation();
        log.debug("Generated price for route {} -> {}: Rs. {}", from, to, price);
        return price;
    }

    private int getPeakHourSurge() {
        int hour = LocalTime.now().getHour();
        return (hour >= PEAK_HOUR_START && hour <= PEAK_HOUR_END) ? PEAK_HOUR_SURGE : 0;
    }

    private int getRandomFluctuation() {
        return random.nextInt(MAX_FLUCTUATION);
    }
}