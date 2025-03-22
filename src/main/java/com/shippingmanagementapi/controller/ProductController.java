package com.shippingmanagementapi.controller;

import com.shippingmanagementapi.dto.AmazonProduct;
import com.shippingmanagementapi.service.AmazonProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final AmazonProductService amazonService;

    @GetMapping("/search")
    public ResponseEntity<AmazonProduct> searchProduct(@RequestParam String asin, @RequestParam(defaultValue = "US") String country) {

        log.info("Searching for product with ASIN: {} in country: {}", asin, country);
        AmazonProduct product = amazonService.getProductDetails(asin, country);
        log.info("Successfully retrieved product details for ASIN: {}", asin);

        return ResponseEntity.ok(product);
    }
}