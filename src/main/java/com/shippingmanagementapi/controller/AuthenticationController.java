package com.shippingmanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shippingmanagementapi.dto.auth.AuthenticationRequest;
import com.shippingmanagementapi.dto.auth.AuthenticationResponse;
import com.shippingmanagementapi.dto.auth.RegistrationRequest;
import com.shippingmanagementapi.dto.auth.RegistrationResponse;
import com.shippingmanagementapi.service.AuthenticationService;
import com.shippingmanagementapi.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600) // CORs
public class AuthenticationController {

    private final AuthenticationService service;
    private final RegistrationService registrationService;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Authentication request has been received from user {}",request.getEmail());
        AuthenticationResponse authenticate = service.authenticate(request);
        log.info(String.valueOf(authenticate));
        return ResponseEntity.ok(authenticate);
    }


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) throws JsonProcessingException {
        log.info("Registration request has been received from user {}",objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));

        return ResponseEntity.ok(registrationService.registerUser(request));
    }
} 