package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PriceScheduler {

    private final MockPriceGenerator priceGenerator;
    private final KafkaProducerService kafkaProducerService;

    @Value("${flight.routes}")
    private String routes;

    public PriceScheduler(MockPriceGenerator priceGenerator,
                          KafkaProducerService kafkaProducerService) {
        this.priceGenerator = priceGenerator;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedRateString = "${flight.check-interval-ms}")
    public void checkPrices() {
        List<String> routeList = List.of(routes.split(","));
        routeList.forEach(route -> {
            String[] cities = route.split("->");
            int price = priceGenerator.generatePrice(cities[0].trim(), cities[1].trim());
            log.info("Price check for route {}: Rs. {}", route, price);
            kafkaProducerService.sendPriceUpdate(cities[0].trim(), cities[1].trim(), price);
        });
    }
}