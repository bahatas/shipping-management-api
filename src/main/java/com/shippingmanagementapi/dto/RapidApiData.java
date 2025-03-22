package com.shippingmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapidApiData {
    private String asin;
    private String product_title;
    private String product_price;
    private String product_original_price;
    private String currency;
    private String country;
    private String product_photo;
    private String product_availability;
    private Map<String, String> product_details;
    private List<String> product_photos;
    private Boolean is_prime;
    private Map<String, String> product_information;

    // Getter metodları snake_case'den camelCase'e dönüştürme yapar
    public String getProductTitle() {
        return product_title;
    }

    public String getProductPrice() {
        return product_price;
    }

    public String getProductOriginalPrice() {
        return product_original_price;
    }

    public String getProductPhoto() {
        return product_photo;
    }

    public String getProductAvailability() {
        return product_availability;
    }

    public Map<String, String> getProductDetails() {
        return product_details;
    }

    public List<String> getProductPhotos() {
        return product_photos;
    }

    public Boolean getIsPrime() {
        return is_prime;
    }

    public Map<String, String> getProductInformation() {
        return product_information;
    }
}