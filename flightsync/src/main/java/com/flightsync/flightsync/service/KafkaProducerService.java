package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {

    private static final String TOPIC = "flight-prices";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPriceUpdate(String from, String to, int price) {
        String message = from + "->" + to + ":" + price;
        kafkaTemplate.send(TOPIC, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send price update to Kafka for route {} -> {}: {}",
                                from, to, ex.getMessage());
                    } else {
                        log.info("Price update sent to Kafka for route {} -> {}: Rs. {}",
                                from, to, price);
                    }
                });
    }
}