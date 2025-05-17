package com.shippingmanagementapi.service;

import com.shippingmanagementapi.dto.UserDTO;
import com.shippingmanagementapi.model.User;
import com.shippingmanagementapi.repository.UserRepository;
import com.shippingmanagementapi.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {


    private final UserRepository userRepository;
    private final MapperUtil mapper ;

    public UserService(UserRepository userRepository, MapperUtil mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<UserDTO> findAll() {
        List<User> storedUsers = userRepository.findAll();
        List<UserDTO> userDTOList = storedUsers.stream().map(user -> UserDTO.fromUser(user)).collect(Collectors.toList());

        log.info("Fetched all users. Total count : {}",userDTOList);
        return userDTOList;

    }

    public UserDTO findByEmail(String email) {
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
}