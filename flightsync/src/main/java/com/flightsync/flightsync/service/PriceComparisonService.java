package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PriceComparisonService {

    private final RedisService redisService;

    public PriceComparisonService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void compareAndLog(String route, int currentPrice) {
        Integer lastPrice = redisService.getLastPrice(route);

        if (lastPrice == null) {
            log.info("First price recorded for route {}: Rs. {}", route, currentPrice);
            return;
        }

        int difference = lastPrice - currentPrice;

        if (difference > 0) {
            log.info("Price dropped for route {} by Rs. {}. Previous: Rs. {}, Current: Rs. {}",
                    route, difference, lastPrice, currentPrice);
        } else if (difference < 0) {
            log.info("Price increased for route {} by Rs. {}. Previous: Rs. {}, Current: Rs. {}",
                    route, Math.abs(difference), lastPrice, currentPrice);
        } else {
            log.info("Price unchanged for route {}: Rs. {}", route, currentPrice);
        }
    }
}