package com.flightsync.flightsync.service;

import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final int threshold = 6000;

    public void checkAndAlert(String from, String to, int currentPrice) {
        if (currentPrice < threshold) {
            System.out.println("🚨 ALERT! Price drop detected!");
            System.out.println(from + " -> " + to + " price is now ₹" + currentPrice);
            System.out.println("This is below your threshold of ₹" + threshold);
            System.out.println("-----------------------------------");
        }
    }
}