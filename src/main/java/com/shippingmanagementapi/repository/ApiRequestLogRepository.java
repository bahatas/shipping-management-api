package com.shippingmanagementapi.repository;

import com.shippingmanagementapi.model.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog, Long> {
    @Query("SELECT COUNT(a) FROM ApiRequestLog a WHERE a.asin = :asin " +
            "AND a.country = :country " +
            "AND DATE(a.requestDate) = CURRENT_DATE")
    Integer getDailyRequestCount(String asin, String country);
    
    @Query("SELECT COUNT(a) FROM ApiRequestLog a WHERE a.asin = :asin " +
           "AND a.country = :country")
    Integer getTotalRequestCount(String asin, String country);


    Integer countByAsinAndCountryAndRequestDateBetween(String asin, String country, LocalDateTime withSecond, LocalDateTime now);


    Integer countByAsinAndCountry(String asin, String country);
}