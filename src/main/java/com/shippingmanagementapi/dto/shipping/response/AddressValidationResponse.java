package com.shippingmanagementapi.dto.shipping.response;

import lombok.Data;
import java.util.List;

@Data
public class AddressValidationResponse {
    private List<ValidatedAddress> addresses;

    @Data
    public static class ValidatedAddress {
        private String status;
        private Address originalAddress;
        private Address normalizedAddress;
        private List<String> messages;
    }

    @Data
    public static class Address {
        private String name;
        private String addressLine1;
        private String cityLocality;
        private String stateProvince;
        private String postalCode;
        private String countryCode;
        private Boolean residential;
    }
} 