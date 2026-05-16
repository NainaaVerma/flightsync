package com.flightsync.flightsync.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PriceScheduler {

    private final MockPriceGenerator priceGenerator;
    private final KafkaProducerService kafkaProducerService;

    private final List<String[]> routes = List.of(
            new String[]{"Mumbai", "Delhi"},
            new String[]{"Bangalore", "Mumbai"},
            new String[]{"Delhi", "Chennai"},
            new String[]{"Hyderabad", "Kolkata"}
    );

    public PriceScheduler(MockPriceGenerator priceGenerator, KafkaProducerService kafkaProducerService) {
        this.priceGenerator = priceGenerator;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedRate = 5000)
    public void checkPrices() {
        for (String[] route : routes) {
            int price = priceGenerator.generatePrice(route[0], route[1]);
            System.out.println("💰 Price check -- " + route[0] + " -> " + route[1] + ": ₹" + price);
            kafkaProducerService.sendPriceUpdate(route[0], route[1], price);
        }
    }
}