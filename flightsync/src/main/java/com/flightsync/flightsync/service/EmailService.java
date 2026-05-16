package com.flightsync.flightsync.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAlert(String toEmail, String from, String to, int price, int threshold) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("🚨 Flight Price Alert! " + from + " → " + to);
            message.setText(
                    "Hey!\n\n" +
                            "Flight price dropped!\n\n" +
                            "Route: " + from + " → " + to + "\n" +
                            "Current Price: ₹" + price + "\n" +
                            "Your Threshold: ₹" + threshold + "\n\n" +
                            "Book now before price goes up!\n\n" +
                            "- FlightSync"
            );
            mailSender.send(message);
            System.out.println("📧 Email sent to: " + toEmail);
        } catch (Exception e) {
            System.out.println("❌ Email error: " + e.getMessage());
        }
    }
}