package com.flightsync.flightsync.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PriceScheduler {

    private final MockPriceGenerator priceGenerator;
    private final KafkaProducerService kafkaProducerService;

    public PriceScheduler(MockPriceGenerator priceGenerator, KafkaProducerService kafkaProducerService) {
        this.priceGenerator = priceGenerator;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedRate = 5000)
    public void checkPrices() {
        int price = priceGenerator.generatePrice("Mumbai", "Delhi");
        System.out.println("💰 Price check -- Mumbai -> Delhi: ₹" + price);
        kafkaProducerService.sendPriceUpdate("Mumbai", "Delhi", price);
    }
}