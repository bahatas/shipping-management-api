package com.shippingmanagementapi.dto.shipping.response;

import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ShippingRateResponse {
    private String shipmentId;
    private String carrierId;
    private String serviceCode;
    private RateResponse rateResponse;

    @Data
    public static class RateResponse {
        private List<Rate> rates;
        private String status;
    }

    @Data
    public static class Rate {
        private String rateId;
        private String rateType;
        private String carrierId;
        private Amount shippingAmount;
        private Amount insuranceAmount;
        private Amount confirmationAmount;
        private Amount otherAmount;
        private int zone;
        private String packageType;
        private int deliveryDays;
        private boolean guaranteedService;
        private ZonedDateTime estimatedDeliveryDate;
        private String carrierDeliveryDays;
        private ZonedDateTime shipDate;
        private boolean negotiatedRate;
        private String serviceType;
        private String serviceCode;
        private boolean trackable;
        private String carrierCode;
        private String carrierNickname;
        private String carrierFriendlyName;
    }

    @Data
    public static class Amount {
        private String currency;
        private double amount;
    }
} 