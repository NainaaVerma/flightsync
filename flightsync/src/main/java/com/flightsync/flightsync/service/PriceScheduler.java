package com.flightsync.flightsync.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PriceScheduler {

    private final MockPriceGenerator priceGenerator;
    private final AlertService alertService;

    public PriceScheduler(MockPriceGenerator priceGenerator, AlertService alertService) {
        this.priceGenerator = priceGenerator;
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 5000)
    public void checkPrices() {
        int price = priceGenerator.generatePrice("Mumbai", "Delhi");
        System.out.println("Price check -- Mumbai -> Delhi: ₹" + price);
        alertService.checkAndAlert("Mumbai", "Delhi", price);
    }
}