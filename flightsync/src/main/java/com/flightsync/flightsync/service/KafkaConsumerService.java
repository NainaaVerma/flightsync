package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    private final AlertService alertService;
    private final RedisService redisService;
    private final PriceComparisonService priceComparisonService;

    public KafkaConsumerService(AlertService alertService,
                                RedisService redisService,
                                PriceComparisonService priceComparisonService) {
        this.alertService = alertService;
        this.redisService = redisService;
        this.priceComparisonService = priceComparisonService;
    }

    @KafkaListener(topics = "flight-prices", groupId = "flightsync-group")
    public void consumePriceUpdate(String message) {
        log.info("Received price update from Kafka: {}", message);

        String[] parts = message.split(":");
        String route = parts[0];
        int currentPrice = Integer.parseInt(parts[1]);

        priceComparisonService.compareAndLog(route, currentPrice);
        redisService.savePrice(route, currentPrice);

        String[] cities = route.split("->");
        alertService.checkAndAlert(cities[0], cities[1], currentPrice);
    }
}