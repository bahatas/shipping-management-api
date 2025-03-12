package com.shippingmanagementapi.controller;

import com.shippingmanagementapi.dto.auth.AuthenticationRequest;
import com.shippingmanagementapi.dto.auth.AuthenticationResponse;
import com.shippingmanagementapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600) // CORS desteği eklendi
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Authentication request has been received from user {}",request.getEmail());
        return ResponseEntity.ok(service.authenticate(request));
    }
} 