package com.flightsync.flightsync.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final AlertService alertService;
    private final RedisService redisService;

    public KafkaConsumerService(AlertService alertService, RedisService redisService) {
        this.alertService = alertService;
        this.redisService = redisService;
    }

    @KafkaListener(topics = "flight-prices", groupId = "flightsync-group")
    public void consumePriceUpdate(String message) {
        System.out.println("📥 Received from Kafka: " + message);

        String[] parts = message.split(":");
        String route = parts[0];
        int currentPrice = Integer.parseInt(parts[1]);

        // Redis se last price lo
        Integer lastPrice = redisService.getLastPrice(route);

        if (lastPrice != null) {
            int difference = lastPrice - currentPrice;
            if (difference > 0) {
                System.out.println("📉 Price dropped by ₹" + difference + " since last check!");
            } else if (difference < 0) {
                System.out.println("📈 Price increased by ₹" + Math.abs(difference) + " since last check!");
            }
        }

        // Current price Redis mein save karo
        redisService.savePrice(route, currentPrice);

        // Alert check karo
        String[] cities = route.split("->");
        alertService.checkAndAlert(cities[0], cities[1], currentPrice);
    }
}