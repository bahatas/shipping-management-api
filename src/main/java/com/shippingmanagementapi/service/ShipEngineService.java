package com.shippingmanagementapi.service;


import com.shippingmanagementapi.client.ShipEngine;
import com.shippingmanagementapi.dto.shipping.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
@Slf4j
public class ShipEngineService {

    private final ShipEngine shipEngine;


    public ShipEngineService() {
        this.shipEngine = new ShipEngine("TEST_0qAQGaM6HAvSiTc8mUOioV82oOFcJFjW8W3XZITb/40");
    }

    // Track package method
    public Object trackPackage(String labelId) {
        try {
            return shipEngine.trackUsingLabelId(labelId);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while tracking package: " + e.getMessage());
        }
    }

    // Address validation method
    public Object validateAddress(AddressDTO addressData) {
        try {
            Map<String, String> addressMap = convertAddressToMap(addressData);
            List<Map<String, String>> addresses = List.of(addressMap);
            return shipEngine.validateAddresses(addresses);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while validating address: " + e.getMessage());
        }
    }

    // Get shipping rates method
    public Map<String, Object> getShippingRates(ShipmentRequestDTO request) {
        try {
            Map<String, Object> shipmentDetails = new HashMap<>();
            Map<String, Object> shipment = new HashMap<>();
            
            // Main shipment details
            shipment.put("carrier_id", request.getCarrierId());
            shipment.put("service_code", request.getServiceCode());
            shipment.put("ship_date", request.getShipDate());
            
            // Recipient address
            shipment.put("ship_to", convertAddressToMap(request.getShipTo()));
            
            // Sender address
            shipment.put("ship_from", convertAddressToMap(request.getShipFrom()));
            
            // Package details
            shipment.put("packages", convertPackagesToList(request.getPackages()));
            
            shipmentDetails.put("shipment", shipment);

            //Map<String, String> ratesWithShipmentDetails = shipEngine.getRatesWithShipmentDetails(shipmentDetails);




            Map<String, Object> shipmentDetails2 = Map.ofEntries(
                    Map.entry("shipment", Map.of(
                                    "carrier_id", "se-1234",
                                    "service_code", "usps_first_class_mail",
                                    "external_order_id", "string",
                                    "item", List.of(),
                                    "tax_identifiers", List.of(
                                            Map.of(
                                                    "taxable_entity_type", "shipper",
                                                    "identifier_type", "vat",
                                                    "issuing_authority", "string",
                                                    "value", "string"
                                            )
                                    ),
                                    "external_shipment_id", "string",
                                    "ship_date", "2018-09-23T00:00:00.000Z",
                                    "ship_to", Map.ofEntries(
                                            Map.entry("name", "John Doe"),
                                            Map.entry("phone", "1-123-456-7894"),
                                            Map.entry("company_name", "The Home Depot"),
                                            Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                            Map.entry("address_line2", "Unit 408"),
                                            Map.entry("address_line3", "Building #7"),
                                            Map.entry("city_locality", "Winnipeg"),
                                            Map.entry("state_province", "Manitoba"),
                                            Map.entry("postal_code", "78756"),
                                            Map.entry("country_code", "CA"),
                                            Map.entry("address_residential_indicator", "no")
                                    ),
                                    "ship_from", Map.ofEntries(
                                            Map.entry("name", "John Doe"),
                                            Map.entry("phone", "1-123-456-7894"),
                                            Map.entry("company_name", "The Home Depot"),
                                            Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                            Map.entry("address_line2", "Unit 408"),
                                            Map.entry("address_line3", "Building #7"),
                                            Map.entry("city_locality", "Winnipeg"),
                                            Map.entry("state_province", "Manitoba"),
                                            Map.entry("postal_code", "78756"),
                                            Map.entry("country_code", "CA"),
                                            Map.entry("address_residential_indicator", "no")
                                    )
                            )
                    ));

            //Map<String, Object> result = shipEngine.getRatesWithShipmentDetails(shipmentDetails2);
            Map<String, Object> result = new HashMap<>();

            System.out.println("result = " + result);



            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while getting shipping rates: " + e.getMessage());
        }
    }
    
    private Map<String, String> convertAddressToMap(AddressDTO address) {
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("name", address.getName());
        addressMap.put("phone", address.getPhone());
        addressMap.put("company_name", address.getCompanyName());
        addressMap.put("address_line1", address.getAddressLine1());
        addressMap.put("address_line2", address.getAddressLine2());
        addressMap.put("address_line3", address.getAddressLine3());
        addressMap.put("city_locality", address.getCityLocality());
        addressMap.put("state_province", address.getStateProvince());
        addressMap.put("postal_code", address.getPostalCode());
        addressMap.put("country_code", address.getCountryCode());
        addressMap.put("address_residential_indicator", address.getAddressResidentialIndicator());
        return addressMap;
    }
    
    private List<Map<String, Object>> convertPackagesToList(List<PackageDTO> packages) {
        return packages.stream().map(pkg -> {
            Map<String, Object> packageMap = new HashMap<>();
            packageMap.put("package_code", pkg.getPackageCode());
            
            Map<String, Object> weight = new HashMap<>();
            weight.put("value", pkg.getWeight().getValue());
            weight.put("unit", pkg.getWeight().getUnit());
            packageMap.put("weight", weight);
            
            Map<String, Object> dimensions = new HashMap<>();
            dimensions.put("unit", pkg.getDimensions().getUnit());
            dimensions.put("length", pkg.getDimensions().getLength());
            dimensions.put("width", pkg.getDimensions().getWidth());
            dimensions.put("height", pkg.getDimensions().getHeight());
            packageMap.put("dimensions", dimensions);
            System.out.println("packageMap populated = " + packageMap);


            return packageMap;
        }).toList();
    }


    // Track package method
    public Map<String, Object> listCarriers() {
        Map<String, Object> stringStringMap = new HashMap<>();
        try {
           stringStringMap = (Map<String, Object>) shipEngine.getListOfCarriers();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while stringStringMap package: " + e.getMessage());
        }
        return stringStringMap;
    }
} 