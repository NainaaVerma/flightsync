package com.flightsync.flightsync.service;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.model.UserAlert;
import com.flightsync.flightsync.repository.UserAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserAlertService {

    private final UserAlertRepository userAlertRepository;

    public UserAlertService(UserAlertRepository userAlertRepository) {
        this.userAlertRepository = userAlertRepository;
    }

    public UserAlert createAlert(AlertRequest request) {
        if (userAlertRepository.existsByEmailAndFromCityAndToCity(
                request.getEmail(), request.getFrom(), request.getTo())) {
            throw new IllegalArgumentException("Alert already exists for this route and email");
        }

        UserAlert alert = UserAlert.builder()
                .email(request.getEmail())
                .fromCity(request.getFrom())
                .toCity(request.getTo())
                .threshold(request.getThreshold())
                .build();

        UserAlert saved = userAlertRepository.save(alert);
        log.info("Alert created for email {} on route {} -> {} at threshold Rs. {}",
                request.getEmail(), request.getFrom(), request.getTo(), request.getThreshold());
        return saved;
    }

    public List<UserAlert> getActiveAlertsForRoute(String fromCity, String toCity) {
        return userAlertRepository.findByFromCityAndToCityAndActiveTrue(fromCity, toCity);
    }

    public List<UserAlert> getAlertsByEmail(String email) {
        return userAlertRepository.findByEmailAndActiveTrue(email);
    }

    public void deactivateAlert(Long alertId) {
        userAlertRepository.findById(alertId).ifPresent(alert -> {
            alert.setActive(false);
            userAlertRepository.save(alert);
            log.info("Alert deactivated for id {}", alertId);
        });
    }
}