package com.flightsync.flightsync.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class AlertService {

    private final int threshold = 6000;
    private final EmailService emailService;

    @Value("${alert.emails}")
    private String alertEmails;

    public AlertService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void checkAndAlert(String from, String to, int currentPrice) {
        if (currentPrice < threshold) {
            System.out.println("🚨 ALERT! Price drop detected!");
            System.out.println(from + " -> " + to + " price is now ₹" + currentPrice);
            System.out.println("This is below your threshold of ₹" + threshold);
            System.out.println("-----------------------------------");

            List<String> emails = Arrays.asList(alertEmails.split(","));
            for (String email : emails) {
                emailService.sendAlert(email.trim(), from, to, currentPrice, threshold);
            }
        }
    }
}