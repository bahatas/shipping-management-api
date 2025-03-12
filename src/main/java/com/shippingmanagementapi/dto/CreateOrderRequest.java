package com.shippingmanagementapi.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CreateOrderRequest {
    private String storeName;
    private String productName;
    private String asinCode;
    private String country;
    private BigDecimal orderAmount;
    private BigDecimal additionalFee;
    private ShippingAddressDTO shippingAddress;
} 