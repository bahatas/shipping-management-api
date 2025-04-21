package com.shippingmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
@EnableCaching
public class ShippingManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingManagementApiApplication.class, args);

    }

    @PostConstruct
    public void prePersist() {
    }
}
