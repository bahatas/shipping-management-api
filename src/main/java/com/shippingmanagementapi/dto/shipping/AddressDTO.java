package com.shippingmanagementapi.dto.shipping;

import lombok.Data;

@Data
public class AddressDTO {
    private String name;
    private String phone;
    private String companyName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String cityLocality;
    private String stateProvince;
    private String postalCode;
    private String countryCode;
    private String addressResidentialIndicator;
} 