package com.flightsync.flightsync.controller;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class FlightAlertController {

    private final RedisService redisService;

    public FlightAlertController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/set")
    public ResponseEntity<String> setAlert(@RequestBody AlertRequest request) {
        String route = request.getFrom() + "->" + request.getTo();
        redisService.savePrice(route + ":threshold", request.getThreshold());
        return ResponseEntity.ok("✅ Alert set for " + route + " at ₹" + request.getThreshold());
    }

    @GetMapping("/price/{from}/{to}")
    public ResponseEntity<String> getCurrentPrice(@PathVariable String from, @PathVariable String to) {
        String route = from + "->" + to;
        Integer price = redisService.getLastPrice(route);
        if (price == null) {
            return ResponseEntity.ok("No price data yet for " + route);
        }
        return ResponseEntity.ok("Current price for " + route + ": ₹" + price);
    }
}