package com.flightsync.flightsync.service;

public interface NotificationService {
    void sendNotification(String to, String from, String destination, int price, int threshold);
}