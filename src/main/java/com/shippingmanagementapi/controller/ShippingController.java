package com.shippingmanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shippingmanagementapi.service.ShipEngineService;
import com.shippingmanagementapi.dto.shipping.ShipmentRequestDTO;
import com.shippingmanagementapi.dto.shipping.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shipping")
@Slf4j
public class ShippingController {

    private final ShipEngineService shipEngineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public ShippingController(ShipEngineService shipEngineService) {
        this.shipEngineService = shipEngineService;
    }

    @GetMapping("/track/{labelId}")
    public ResponseEntity<?> trackShipment(@PathVariable String labelId) {
        return ResponseEntity.ok(shipEngineService.trackPackage(labelId));
    }

    @PostMapping("/validate-address")
    public ResponseEntity<?> validateAddress(@RequestBody AddressDTO addressData) {
        return ResponseEntity.ok(shipEngineService.validateAddress(addressData));
    }

    @PostMapping("/rates")
    public ResponseEntity<Map<String,Object>> getShippingRates(@RequestBody ShipmentRequestDTO request) throws JsonProcessingException {
        log.info("Received shipping rate request : " + objectMapper.writeValueAsString(request));
        Map<String, Object> response = shipEngineService.getShippingRates(request);
        log.info("Received shipping rate request : " +response);

        return ResponseEntity.ok(response);

    }


    @GetMapping("/carriers")
    public ResponseEntity<Map<String,Object>> listCarriers() throws JsonProcessingException {
        log.info("Received getShippingCarriers: " );
        Map<String, Object> response = shipEngineService.listCarriers();
        log.info("Received shipping rate request : " +response);

        return ResponseEntity.ok(response);

    }

//    @PostMapping("/rates")
//    public ResponseEntity<ShippingRateResponse> getShippingRates(@RequestBody ShipmentRequestDTO request) {
//        return ResponseEntity.ok(shipEngineService.getShippingRates(request));
//    }


} 