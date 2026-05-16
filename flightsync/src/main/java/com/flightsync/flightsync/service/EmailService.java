package com.flightsync.flightsync.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService implements NotificationService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(String toEmail, String from, String destination, int price, int threshold) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Flight Price Alert: " + from + " to " + destination);
            message.setText(
                    "Hello,\n\n" +
                            "A price drop has been detected for your tracked route.\n\n" +
                            "Route: " + from + " to " + destination + "\n" +
                            "Current Price: Rs. " + price + "\n" +
                            "Your Threshold: Rs. " + threshold + "\n\n" +
                            "We recommend booking at the earliest.\n\n" +
                            "Regards,\n" +
                            "FlightSync"
            );
            mailSender.send(message);
            log.info("Alert email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send alert email to: {}. Error: {}", toEmail, e.getMessage());
        }
    }
}