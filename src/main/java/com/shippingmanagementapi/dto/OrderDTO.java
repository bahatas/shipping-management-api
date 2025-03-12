package com.shippingmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String orderNumber;
    private String storeName;
    private String status;
    private String productName;
    private String asinCode;
    private String country;
    private BigDecimal orderAmount;
    private BigDecimal additionalFee;
    private BigDecimal totalAmount;
    private String trackingLink;
    private LocalDateTime orderDate;
    private LocalDateTime approvedAt;
}
