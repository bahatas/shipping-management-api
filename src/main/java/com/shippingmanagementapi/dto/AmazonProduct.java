package com.shippingmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmazonProduct {
    private String asin;
    private String productTitle;
    private String productPrice;
    private String productOriginalPrice;
    private String currency;
    private String country;
    private String productPhoto;
    private String productAvailability;
    private ProductDetails productDetails;
    private List<String> productPhotos;
    private ProductShippingDetails productShippingDetails;

}