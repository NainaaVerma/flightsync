package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void savePrice(String route, int price) {
        redisTemplate.opsForValue().set(route, String.valueOf(price));
        log.info("Price saved to Redis for route {}: Rs. {}", route, price);
    }

    public Integer getLastPrice(String route) {
        String price = redisTemplate.opsForValue().get(route);
        if (price == null) {
            log.debug("No price found in Redis for route {}", route);
            return null;
        }
        return Integer.parseInt(price);
    }
}