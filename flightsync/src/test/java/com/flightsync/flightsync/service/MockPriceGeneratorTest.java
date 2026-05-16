package com.flightsync.flightsync.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MockPriceGeneratorTest {

    private MockPriceGenerator priceGenerator;

    @BeforeEach
    void setUp() {
        priceGenerator = new MockPriceGenerator();
    }

    @Test
    @DisplayName("Generated price should be greater than base price")
    void shouldGeneratePriceGreaterThanBasePrice() {
        int price = priceGenerator.generatePrice("Mumbai", "Delhi");
        assertTrue(price >= 5000, "Price should be at least base price of Rs. 5000");
    }

    @Test
    @DisplayName("Generated price should be within expected range")
    void shouldGeneratePriceWithinExpectedRange() {
        int price = priceGenerator.generatePrice("Mumbai", "Delhi");
        assertTrue(price >= 5000 && price <= 10000,
                "Price should be between Rs. 5000 and Rs. 10000");
    }

    @Test
    @DisplayName("Should generate different prices for different routes")
    void shouldGeneratePricesForDifferentRoutes() {
        int price1 = priceGenerator.generatePrice("Mumbai", "Delhi");
        int price2 = priceGenerator.generatePrice("Bangalore", "Chennai");
        assertNotNull(price1);
        assertNotNull(price2);
    }
}