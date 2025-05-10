package com.shippingmanagementapi.service;

import com.shippingmanagementapi.dto.UserDTO;
import com.shippingmanagementapi.model.User;
import com.shippingmanagementapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User does not exist with this email: " + email));
        log.info("User found : {}", user.getEmail());

        UserDTO userDTO = UserDTO.fromUser(user);
        log.info("UserDTO created : {}", userDTO.getEmail());

        return userDTO;
    }
} 