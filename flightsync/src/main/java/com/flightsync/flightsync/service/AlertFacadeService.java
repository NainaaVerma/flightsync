package com.flightsync.flightsync.service;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertFacadeService {

    private final RedisService redisService;

    public AlertFacadeService(RedisService redisService) {
        this.redisService = redisService;
    }

    public ApiResponse<String> setAlert(AlertRequest request) {
        try {
            validateRequest(request);
            String route = buildRoute(request.getFrom(), request.getTo());
            redisService.savePrice(route + ":threshold", request.getThreshold());
            log.info("Alert set for route {} at threshold Rs. {}", route, request.getThreshold());
            return ApiResponse.success("Alert set successfully for " + route
                    + " at Rs. " + request.getThreshold(), null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid alert request: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        }
    }

    public ApiResponse<Integer> getCurrentPrice(String from, String to) {
        String route = buildRoute(from, to);
        Integer price = redisService.getLastPrice(route);
        if (price == null) {
            log.warn("No price data found for route {}", route);
            return ApiResponse.error("No price data found for route " + route);
        }
        return ApiResponse.success("Current price fetched successfully", price);
    }

    private void validateRequest(AlertRequest request) {
        if (request.getFrom() == null || request.getFrom().isBlank()) {
            throw new IllegalArgumentException("Source city cannot be empty");
        }
        if (request.getTo() == null || request.getTo().isBlank()) {
            throw new IllegalArgumentException("Destination city cannot be empty");
        }
        if (request.getThreshold() <= 0) {
            throw new IllegalArgumentException("Threshold must be greater than 0");
        }
    }

    private String buildRoute(String from, String to) {
        return from + "->" + to;
    }
}