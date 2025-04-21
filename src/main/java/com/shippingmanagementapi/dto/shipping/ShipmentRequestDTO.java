package com.shippingmanagementapi.dto.shipping;

import lombok.Data;
import java.util.List;

@Data
public class ShipmentRequestDTO {
    private String carrierId;
    private String serviceCode;
    private String shipDate;
    private AddressDTO shipTo;
    private AddressDTO shipFrom;
    private List<PackageDTO> packages;
} 