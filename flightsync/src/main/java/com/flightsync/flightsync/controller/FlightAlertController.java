package com.flightsync.flightsync.controller;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.model.ApiResponse;
import com.flightsync.flightsync.model.UserAlert;
import com.flightsync.flightsync.service.AlertFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/alerts")
public class FlightAlertController {

    private final AlertFacadeService alertFacadeService;

    public FlightAlertController(AlertFacadeService alertFacadeService) {
        this.alertFacadeService = alertFacadeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserAlert>> createAlert(@RequestBody AlertRequest request) {
        log.info("Received alert creation request for route {} -> {}", request.getFrom(), request.getTo());
        ApiResponse<UserAlert> response = alertFacadeService.setAlert(request);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/price/{from}/{to}")
    public ResponseEntity<ApiResponse<Integer>> getCurrentPrice(
            @PathVariable String from,
            @PathVariable String to) {
        log.info("Received price fetch request for route {} -> {}", from, to);
        ApiResponse<Integer> response = alertFacadeService.getCurrentPrice(from, to);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<ApiResponse<List<UserAlert>>> getAlertsByEmail(
            @PathVariable String email) {
        log.info("Fetching alerts for email {}", email);
        ApiResponse<List<UserAlert>> response = alertFacadeService.getAlertsByEmail(email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<ApiResponse<String>> deactivateAlert(@PathVariable Long alertId) {
        log.info("Deactivating alert with id {}", alertId);
        ApiResponse<String> response = alertFacadeService.deactivateAlert(alertId);
        return ResponseEntity.ok(response);
    }
}