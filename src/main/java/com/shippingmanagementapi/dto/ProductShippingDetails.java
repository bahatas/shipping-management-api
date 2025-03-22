package com.shippingmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductShippingDetails {
    private String asin;
    private String title;
    private BigDecimal price;
    private String currency;
    
    // Boyutlar
    private String dimensions;      // "7 x 5 x 4 inches"
    private Double weight;          // "6.7 ounces"
    private String weightUnit;      // "ounces" veya "Kilograms"
    
    // Stok bilgisi
    private String availability;    // "Only 16 left in stock - order soon."
    private Boolean isPrime;
    
    // Kargo bilgileri için önemli olabilecek diğer detaylar
    private String manufacturer;
    private String itemModelNumber;
    private String packageDimensions;
} 