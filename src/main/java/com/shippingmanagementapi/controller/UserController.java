package com.shippingmanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shippingmanagementapi.dto.UserDTO;
import com.shippingmanagementapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String email) {
        log.info("Get user by email request received for {}",email);

        return Optional.of(userService.findByUsername(email))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping()
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        log.info("Update user request received for {}", userDTO.toString());
        UserDTO update = userService.update(userDTO);
       return ResponseEntity.ok().body(update);
    }
} 