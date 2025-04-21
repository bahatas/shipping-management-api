package com.shippingmanagementapi.dto.shipping.response;

import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class TrackingResponse {
    private String trackingNumber;
    private String status;
    private String statusDescription;
    private String carrierStatusCode;
    private String carrierDetailCode;
    private String carrierStatusDescription;
    private ZonedDateTime shipDate;
    private ZonedDateTime estimatedDeliveryDate;
    private List<TrackingEvent> trackingEvents;

    @Data
    public static class TrackingEvent {
        private ZonedDateTime eventDate;
        private String status;
        private String description;
        private String cityLocality;
        private String stateProvince;
        private String postalCode;
        private String countryCode;
    }
} 