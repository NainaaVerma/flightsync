package com.flightsync.flightsync.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "flight-prices";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPriceUpdate(String from, String to, int price) {
        String message = from + "->" + to + ":" + price;
        kafkaTemplate.send(TOPIC, message);
        System.out.println("📤 Sent to Kafka: " + message);
    }
}