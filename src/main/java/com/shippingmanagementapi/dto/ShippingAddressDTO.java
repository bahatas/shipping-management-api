package com.shippingmanagementapi.dto;

import lombok.Data;

@Data
public class ShippingAddressDTO {
    private String country;
    private String postalCode;
    private String addressLine;
} 