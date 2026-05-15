package com.flightsync.flightsync.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void savePrice(String route, int price) {
        redisTemplate.opsForValue().set(route, String.valueOf(price));
        System.out.println("💾 Saved to Redis: " + route + " = ₹" + price);
    }

    public Integer getLastPrice(String route) {
        String price = redisTemplate.opsForValue().get(route);
        if (price == null) return null;
        return Integer.parseInt(price);
    }
}