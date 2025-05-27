package com.shippingmanagementapi.service;


import com.shippingmanagementapi.dto.auth.RegistrationRequest;
import com.shippingmanagementapi.dto.auth.RegistrationResponse;
import com.shippingmanagementapi.exception.UsernameAlreadyExistsException;
import com.shippingmanagementapi.model.User;
import com.shippingmanagementapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * Service for handling user registration operations
 */
@Service
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationService(UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegistrationResponse registerUser(RegistrationRequest request) {
        if(Objects.isNull(request) || StringUtils.isAnyBlank(request.getEmail(),request.getPassword())){

            throw new IllegalArgumentException("Registration paylod is not valid! Check username and password.");
        }
        // Check if username already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UsernameAlreadyExistsException("Email already in use: " + request.getEmail());
//               RegistrationResponse response = RegistrationResponse.builder().message(request.getEmail()).message("User has been created successfully please use /login").build();

        }

        User newUser = new User();

        // Hash the password
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());

        // Set user as enabled by default since we're not using email verification
        newUser.setEnabled(true);

        // Save the user

        User storedUser = userRepository.save(newUser);
        log.info("Registration has been completed for user : {}",storedUser.toString());
        RegistrationResponse response = RegistrationResponse.builder().userName(storedUser.getEmail()).message("User has been created successfully please use /login").build();

        log.info(response.toString());
        return response;
    }

    /**
     * Checks if a username is available for registration
     *
     * @param username The username to check
     * @return true if the username is available
     */
    public boolean checkUsernameAvailability(String username) {
        return !userRepository.existsByEmail(username);
    }

    /**
     * Checks if an email is available for registration
     *
     * @param email The email to check
     * @return true if the email is available
     */
    public boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);
    }


    /**
     * Verifies a user account using the provided token
     *
     * @param token The verification token
     * @return true if verification was successful
     */
//    @Transactional
//    public boolean verifyAccount(String token) {
//        User user = userRepository.findByVerificationToken(token)
//                .orElseThrow(() -> new RuntimeException("Invalid verification token"));
//
//        user.setVerificationToken(null);
//        user.setEnabled(true);
//        userRepository.save(user);
//
//        return true;
//    }

}