package com.flightsync.flightsync.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PriceScheduler {

    private final MockPriceGenerator priceGenerator;

    public PriceScheduler(MockPriceGenerator priceGenerator) {
        this.priceGenerator = priceGenerator;
    }

    @Scheduled(fixedRate = 5000)
    public void checkPrices() {
        int price = priceGenerator.generatePrice("Mumbai", "Delhi");
        System.out.println("Price check -- Mumbai -> Delhi: ₹" + price);
    }
}
