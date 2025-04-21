package com.shippingmanagementapi.client;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;

public class ShipEngine {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private static final String API_BASE_URL = "https://docs.shipstation.com/_mock/openapi/v2";

    public ShipEngine(String apiKey) {
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-Key", apiKey);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    // SDK ile aynı metod ismi
    public Map<String, Object> getRatesWithShipmentDetails(Map<String, Object> shipmentDetails) {
        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(shipmentDetails, createHeaders());
            return (Map<String, Object>) restTemplate.exchange(
                API_BASE_URL + "/rates",
                HttpMethod.POST,
                entity,
                Object.class
            ).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error getting rates: " + e.getMessage());
        }
    }

    // SDK ile aynı metod ismi
    public Map<String, Object> trackUsingLabelId(String labelId) {
        try {
            HttpEntity<?> entity = new HttpEntity<>(createHeaders());
            return (Map<String, Object>) restTemplate.exchange(
                API_BASE_URL + "/tracking/" + labelId,
                HttpMethod.GET,
                entity,
                Object.class
            ).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error tracking package: " + e.getMessage());
        }
    }

    // SDK ile aynı metod ismi
    public Object validateAddresses(List<Map<String, String>> addresses) {
        try {
            HttpEntity<List<Map<String, String>>> entity = new HttpEntity<>(addresses, createHeaders());
            Object body = restTemplate.exchange(
                    API_BASE_URL + "/addresses/validate",
                    HttpMethod.POST,
                    entity,
                    Object.class
            ).getBody();
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Error validating address: " + e.getMessage());
        }
    }



    // SDK ile aynı metod ismi
    public Object getListOfCarriers() {
        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(createHeaders());

            return restTemplate.exchange(
                    API_BASE_URL + "/carriers",
                    HttpMethod.GET,
                    entity,
                    Object.class
            ).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error getting carriers: " + e.getMessage());
        }
    }
} 