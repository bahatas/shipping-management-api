package com.shippingmanagementapi.service;

import com.shippingmanagementapi.dto.UserDTO;
import com.shippingmanagementapi.model.User;
import com.shippingmanagementapi.repository.UserRepository;
import com.shippingmanagementapi.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final MapperUtil mapper ;
    private final PasswordEncoder passwordEncoder;


    public List<UserDTO> findAll() {
        List<User> storedUsers = userRepository.findAll();
        List<UserDTO> userDTOList = storedUsers.stream().map(user -> UserDTO.fromUser(user)).collect(Collectors.toList());

        log.info("Fetched all users. Total count : {}",userDTOList);
        return userDTOList;

    }

    public UserDTO findByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User does not exist with this email: " + email));
        log.info("User found : {}", user.getEmail());

        //UserDTO userDTO = UserDTO.fromUser(user);
        UserDTO userDTO = mapper.convert(user, new UserDTO());
        log.info("UserDTO created : '{}', {}", userDTO.getEmail(), userDTO.toString());

        return userDTO;
    }

    public User findUserIdByEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.get();
    }


    public UserDTO update(UserDTO userDTO) {
        Optional<User> byEmail = userRepository.findByEmail(userDTO.getEmail());
        log.info("Username has been found updating : {}", userDTO.getEmail());

        if(!byEmail.isPresent()){
            log.warn("Username is not exist for this email {}", userDTO.getEmail());
            throw new NoSuchElementException("Email is not matching with records");
        }
        User storedUser = byEmail.get();
        storedUser.setPhone(userDTO.getPhone());
        storedUser.setUpdatedAt(LocalDateTime.now());
        storedUser.setFullName(userDTO.getFullName());
        if(!StringUtils.isBlank(userDTO.getPassword()) ){
           String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
           storedUser.setPassword(encodedPassword);
        }
        User updatedUser = userRepository.save(storedUser);
        return mapper.convert(updatedUser, new UserDTO());
    }
}