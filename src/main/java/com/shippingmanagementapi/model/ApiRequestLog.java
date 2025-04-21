package com.shippingmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_request_logs")
@Data
@NoArgsConstructor
public class ApiRequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String asin;
    private String country;
    
    @Column(columnDefinition = "TEXT")
    private String responseData;
    
    private Integer dailyCounter;
    private Integer totalCounter;
    
    private LocalDateTime requestDate;
    private Boolean fromCache;
}