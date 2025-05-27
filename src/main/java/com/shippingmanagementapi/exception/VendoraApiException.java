package com.shippingmanagementapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class VendoraApiException extends RuntimeException {
    public VendoraApiException(String message) {
        super(message);
    }
}