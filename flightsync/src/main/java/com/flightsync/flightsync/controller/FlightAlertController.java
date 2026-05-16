package com.flightsync.flightsync.controller;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.model.ApiResponse;
import com.flightsync.flightsync.service.AlertFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/alerts")
public class FlightAlertController {

    private final AlertFacadeService alertFacadeService;

    public FlightAlertController(AlertFacadeService alertFacadeService) {
        this.alertFacadeService = alertFacadeService;
    }

    @PostMapping("/set")
    public ResponseEntity<ApiResponse<String>> setAlert(@RequestBody AlertRequest request) {
        log.info("Received alert set request for route {} -> {}", request.getFrom(), request.getTo());
        ApiResponse<String> response = alertFacadeService.setAlert(request);
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
}