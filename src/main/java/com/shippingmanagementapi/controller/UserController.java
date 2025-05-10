package com.shippingmanagementapi.controller;

import com.shippingmanagementapi.dto.UserDTO;
import com.shippingmanagementapi.model.User;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String email) {
        log.info("Get user by email request received for {}",email);

        return Optional.of(userService.findByEmail(email))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 