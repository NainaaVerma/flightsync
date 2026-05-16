package com.flightsync.flightsync.service;

import com.flightsync.flightsync.model.UserAlert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlertService {

    private final List<NotificationService> notificationServices;
    private final UserAlertService userAlertService;

    public AlertService(List<NotificationService> notificationServices,
                        UserAlertService userAlertService) {
        this.notificationServices = notificationServices;
        this.userAlertService = userAlertService;
    }

    public void checkAndAlert(String from, String to, int currentPrice) {
        List<UserAlert> activeAlerts = userAlertService.getActiveAlertsForRoute(from, to);

        activeAlerts.forEach(alert -> {
            if (currentPrice < alert.getThreshold()) {
                log.info("Price drop detected for route {} -> {}. Current: Rs. {}, Threshold: Rs. {}",
                        from, to, currentPrice, alert.getThreshold());
                notificationServices.forEach(service ->
                        service.sendNotification(
                                alert.getEmail(), from, to, currentPrice, alert.getThreshold()
                        )
                );
            }
        });
    }
}