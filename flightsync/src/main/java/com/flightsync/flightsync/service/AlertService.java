package com.flightsync.flightsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class AlertService {

    private final List<NotificationService> notificationServices;

    @Value("${alert.emails}")
    private String alertEmails;

    @Value("${alert.default-threshold}")
    private int defaultThreshold;

    public AlertService(List<NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    public void checkAndAlert(String from, String to, int currentPrice) {
        if (currentPrice < defaultThreshold) {
            log.info("Price drop detected for route {} -> {}. Current price: {}, Threshold: {}",
                    from, to, currentPrice, defaultThreshold);

            List<String> emails = Arrays.asList(alertEmails.split(","));
            for (String email : emails) {
                notificationServices.forEach(service ->
                        service.sendNotification(email.trim(), from, to, currentPrice, defaultThreshold)
                );
            }
        }
    }
}