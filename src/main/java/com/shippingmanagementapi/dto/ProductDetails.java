package com.shippingmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    private String brand;
    private String operatingSystem;
    private String ramMemory;
    private String cpuModel;
    private String memoryStorage;
    private String screenSize;
    private String resolution;
}