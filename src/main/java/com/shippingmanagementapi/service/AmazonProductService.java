package com.shippingmanagementapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shippingmanagementapi.dto.*;
import com.shippingmanagementapi.exception.ProductNotFoundException;
import com.shippingmanagementapi.model.ApiRequestLog;
import com.shippingmanagementapi.repository.ApiRequestLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class AmazonProductService {

    @Value("${rapidapi.key}")
    private String apiKey;

    @Value("${rapidapi.host}")
    private String apiHost;

    private final RestTemplate restTemplate;

    @Autowired
    private ApiRequestLogRepository apiRequestLogRepository;

    @Autowired
    private ObjectMapper objectMapper; // JSON dönüşümü için

    @Cacheable(
            value = "asinCache",
            key = "#asin + '_' + #country",
            unless = "#result == null"
    )
    public AmazonProduct getProductDetails(String asin, String country) {
        log.info("Fetching product details for ASIN: {} and country: {}", asin, country);

        try {
            RapidApiResponse response = fetchProductFromApi(asin, country);
            RapidApiData data = response.getData();
            Map<String, String> productInfo = data.getProductInformation();

            return AmazonProduct.builder()
                    .asin(data.getAsin())
                    .productTitle(data.getProductTitle())
                    .productPrice(data.getProductPrice())
                    .productOriginalPrice(data.getProductOriginalPrice())
                    .currency(data.getCurrency())
                    .country(data.getCountry())
                    .productPhoto(data.getProductPhoto())
                    .productAvailability(data.getProductAvailability())
                    .productPhotos(data.getProductPhotos())
                    .productDetails(mapToProductDetails(data.getProductDetails()))
                    .productShippingDetails(buildShippingDetails(data, productInfo))
                    .build();

        } catch (Exception e) {
            log.error("Error fetching product details for ASIN: {}. Error: {}", asin, e.getMessage());
            throw new ProductNotFoundException("Failed to fetch product details for ASIN: " + asin);
        }
    }

    private ProductShippingDetails buildShippingDetails(RapidApiData data, Map<String, String> productInfo) {
        return ProductShippingDetails.builder()
                .asin(data.getAsin())
                .title(data.getProductTitle())
                .price(extractPrice(data.getProductPrice()))
                .currency(data.getCurrency())
                .dimensions(productInfo.get("Product Dimensions"))
                .weight(extractWeight(null == productInfo.get("Item Weight") ? productInfo.get("Package Dimensions") :productInfo.get("Item Weight") ))
                .weightUnit(extractWeightUnit(productInfo.get("Item Weight")))
                .availability(data.getProductAvailability())
                .isPrime(data.getIsPrime())
                .manufacturer(productInfo.get("Manufacturer"))
                .itemModelNumber(productInfo.get("Item model number"))
                .packageDimensions(productInfo.get("Package Dimensions"))
                .build();
    }

    private BigDecimal extractPrice(String priceStr) {
        if (priceStr == null) return null;
        try {
            return new BigDecimal(priceStr.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            log.warn("Could not parse price: {}", priceStr);
            return null;
        }
    }

    private Double extractWeight(String weightStr) {
        if (weightStr == null) {
          return null;
        }
        try {
            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
            Matcher matcher = pattern.matcher(weightStr);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (Exception e) {
            log.warn("Could not parse weight: {}", weightStr);
        }
        return null;
    }

    private String extractWeightUnit(String weightStr) {
        if (weightStr == null) return null;
        if (weightStr.toLowerCase().contains("ounces")) return "ounces";
        if (weightStr.toLowerCase().contains("pounds")) return "pounds";
        if (weightStr.toLowerCase().contains("kilograms")) return "kilograms";
        return null;
    }


    public RapidApiResponse fetchProductFromApi(String asin, String country) throws JsonProcessingException {

        log.info("Cache MISS for ASIN: {} and country: {}. Making API request...", asin, country);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", apiHost);

        String url = String.format("https://real-time-amazon-data.p.rapidapi.com/product-details?asin=%s&country=%s",
                asin, country);

        log.debug("Making API request to URL: {}", url);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<RapidApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                RapidApiResponse.class
        );

        try {
            ApiRequestLog apiresponse = new ApiRequestLog();
            apiresponse.setAsin(asin);
            apiresponse.setCountry(country);
            apiresponse.setResponseData(objectMapper.writeValueAsString(response));
            apiresponse.setRequestDate(LocalDateTime.now());
            apiresponse.setFromCache(false);

            // Calculate daily and monthly counters
            Integer dailyCount = apiRequestLogRepository.countByAsinAndCountryAndRequestDateBetween(
                    asin,
                    country,
                    LocalDateTime.now().withHour(0).withMinute(0).withSecond(0),
                    LocalDateTime.now()
            );
            Integer totalCount = apiRequestLogRepository.countByAsinAndCountry(asin, country);

            apiresponse.setDailyCounter(dailyCount + 1);
            apiresponse.setTotalCounter(totalCount + 1);

            ApiRequestLog save = apiRequestLogRepository.save(apiresponse);
            log.info("Saved daata to db : {}",save.getAsin());


        }catch (Exception e ){
            log.error("Error on populating data for logger " + e.getMessage());
        }

        return response.getBody();

    }


    private ProductDetails mapToProductDetails(Map<String, String> details) {
        if (details == null) return null;

        log.debug("Mapping product details from response data");

        return ProductDetails.builder()
                .brand(details.get("Brand"))
                .operatingSystem(details.get("Operating System"))
                .ramMemory(details.get("Ram Memory Installed Size"))
                .cpuModel(details.get("CPU Model"))
                .memoryStorage(details.get("Memory Storage Capacity"))
                .screenSize(details.get("Screen Size"))
                .resolution(details.get("Resolution"))
                .build();
    }
}