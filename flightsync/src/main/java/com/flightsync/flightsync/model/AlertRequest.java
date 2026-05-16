package com.flightsync.flightsync.model;

import lombok.Data;

@Data
public class AlertRequest {
    private String email;
    private String from;
    private String to;
    private int threshold;
}