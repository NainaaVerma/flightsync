package com.flightsync.flightsync.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceComparisonServiceTest {

    @Mock
    private RedisService redisService;

    @InjectMocks
    private PriceComparisonService priceComparisonService;

    @Test
    @DisplayName("Should log first price when no previous price exists")
    void shouldLogFirstPriceWhenNoPreviousPriceExists() {
        when(redisService.getLastPrice("Mumbai->Delhi")).thenReturn(null);
        priceComparisonService.compareAndLog("Mumbai->Delhi", 6000);
        verify(redisService, times(1)).getLastPrice("Mumbai->Delhi");
    }

    @Test
    @DisplayName("Should detect price drop correctly")
    void shouldDetectPriceDrop() {
        when(redisService.getLastPrice("Mumbai->Delhi")).thenReturn(8000);
        priceComparisonService.compareAndLog("Mumbai->Delhi", 6000);
        verify(redisService, times(1)).getLastPrice("Mumbai->Delhi");
    }

    @Test
    @DisplayName("Should detect price increase correctly")
    void shouldDetectPriceIncrease() {
        when(redisService.getLastPrice("Mumbai->Delhi")).thenReturn(5000);
        priceComparisonService.compareAndLog("Mumbai->Delhi", 7000);
        verify(redisService, times(1)).getLastPrice("Mumbai->Delhi");
    }

    @Test
    @DisplayName("Should detect unchanged price correctly")
    void shouldDetectUnchangedPrice() {
        when(redisService.getLastPrice("Mumbai->Delhi")).thenReturn(6000);
        priceComparisonService.compareAndLog("Mumbai->Delhi", 6000);
        verify(redisService, times(1)).getLastPrice("Mumbai->Delhi");
    }
}