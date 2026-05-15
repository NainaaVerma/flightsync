package com.flightsync.flightsync.model;

import lombok.Data;

@Data
public class AlertRequest {
    private String from;
    private String to;
    private int threshold;
}