package com.shippingmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapidApiResponse {
    private String status;
    private String request_id;
    private RequestParameters parameters;
    private RapidApiData data;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class RequestParameters {
    private String asin;
    private String country;
}