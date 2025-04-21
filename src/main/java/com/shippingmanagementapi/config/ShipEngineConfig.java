package com.shippingmanagementapi.config;

import com.shippingmanagementapi.client.ShipEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShipEngineConfig {

    @Bean
    public ShipEngine shipEngine(@Value("${shipengine.api.key}") String apiKey) {
        return new ShipEngine(apiKey);
    }
} 