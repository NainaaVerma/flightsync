package com.flightsync.flightsync.service;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.model.ApiResponse;
import com.flightsync.flightsync.model.UserAlert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlertFacadeService {

    private final RedisService redisService;
    private final UserAlertService userAlertService;

    public AlertFacadeService(RedisService redisService, UserAlertService userAlertService) {
        this.redisService = redisService;
        this.userAlertService = userAlertService;
    }

    public ApiResponse<UserAlert> setAlert(AlertRequest request) {
        try {
            validateRequest(request);
            UserAlert alert = userAlertService.createAlert(request);
            return ApiResponse.success("Alert created successfully", alert);
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

    public ApiResponse<List<UserAlert>> getAlertsByEmail(String email) {
        List<UserAlert> alerts = userAlertService.getAlertsByEmail(email);
        return ApiResponse.success("Alerts fetched successfully", alerts);
    }

    public ApiResponse<String> deactivateAlert(Long alertId) {
        userAlertService.deactivateAlert(alertId);
        return ApiResponse.success("Alert deactivated successfully", null);
    }

    private void validateRequest(AlertRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
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