package com.shippingmanagementapi.dto.shipping;

import lombok.Data;

@Data
public class PackageDTO {
    private String packageCode;
    private Weight weight;
    private Dimensions dimensions;
    private InsuredValue insuredValue;
    
    @Data
    public static class Weight {
        private double value;
        private String unit; // ounce, pound vs.
    }
    
    @Data
    public static class Dimensions {
        private String unit; // inch, cm vs.
        private double length;
        private double width;
        private double height;
    }
    
    @Data
    public static class InsuredValue {
        private String currency;
        private double amount;
    }
} 