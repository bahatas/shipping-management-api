package com.shippingmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ShippingManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingManagementApiApplication.class, args);

    }

    @PostConstruct
    public void prePersist() {
    }
}
