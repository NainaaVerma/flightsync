package com.flightsync.flightsync.service;

import com.flightsync.flightsync.model.AlertRequest;
import com.flightsync.flightsync.model.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertFacadeServiceTest {

    @Mock
    private RedisService redisService;

    @InjectMocks
    private AlertFacadeService alertFacadeService;

    private AlertRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new AlertRequest();
        validRequest.setFrom("Mumbai");
        validRequest.setTo("Delhi");
        validRequest.setThreshold(6000);
    }

    @Test
    @DisplayName("Should set alert successfully for valid request")
    void shouldSetAlertSuccessfully() {
        ApiResponse<String> response = alertFacadeService.setAlert(validRequest);
        assertTrue(response.isSuccess());
        verify(redisService, times(1)).savePrice(anyString(), anyInt());
    }

    @Test
    @DisplayName("Should return error when source city is empty")
    void shouldReturnErrorWhenSourceCityEmpty() {
        validRequest.setFrom("");
        ApiResponse<String> response = alertFacadeService.setAlert(validRequest);
        assertFalse(response.isSuccess());
        verify(redisService, never()).savePrice(anyString(), anyInt());
    }

    @Test
    @DisplayName("Should return error when threshold is zero")
    void shouldReturnErrorWhenThresholdIsZero() {
        validRequest.setThreshold(0);
        ApiResponse<String> response = alertFacadeService.setAlert(validRequest);
        assertFalse(response.isSuccess());
        verify(redisService, never()).savePrice(anyString(), anyInt());
    }

    @Test
    @DisplayName("Should return error when no price found in Redis")
    void shouldReturnErrorWhenNoPriceFound() {
        when(redisService.getLastPrice(anyString())).thenReturn(null);
        ApiResponse<Integer> response = alertFacadeService.getCurrentPrice("Mumbai", "Delhi");
        assertFalse(response.isSuccess());
    }

    @Test
    @DisplayName("Should return price successfully when found in Redis")
    void shouldReturnPriceSuccessfully() {
        when(redisService.getLastPrice(anyString())).thenReturn(5500);
        ApiResponse<Integer> response = alertFacadeService.getCurrentPrice("Mumbai", "Delhi");
        assertTrue(response.isSuccess());
        assertEquals(5500, response.getData());
    }
}