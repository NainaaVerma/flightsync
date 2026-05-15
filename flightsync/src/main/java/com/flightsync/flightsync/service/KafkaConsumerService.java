package com.flightsync.flightsync.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final AlertService alertService;

    public KafkaConsumerService(AlertService alertService) {
        this.alertService = alertService;
    }

    @KafkaListener(topics = "flight-prices", groupId = "flightsync-group")
    public void consumePriceUpdate(String message) {
        System.out.println("📥 Received from Kafka: " + message);

        String[] parts = message.split(":");
        String route = parts[0];
        int price = Integer.parseInt(parts[1]);

        String[] cities = route.split("->");
        alertService.checkAndAlert(cities[0], cities[1], price);
    }
}