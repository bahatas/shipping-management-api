package com.shippingmanagementapi.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CacheLoggingAspect {

    @Autowired
    private CacheManager cacheManager;

//    @Before("@annotation(org.springframework.cache.annotation.Cacheable)")
//    public void logCacheAccess(JoinPoint joinPoint) {
//        log.info("Cache check has benn triggered for ASIN API request");
//        Object[] args = joinPoint.getArgs();
//        String asin = (String) args[0];
//        String country = (String) args[1];
//
//        String cacheKey = asin + "_" + country;
//        Cache cache = cacheManager.getCache("asinCache");
//        if (cache != null && cache.get(cacheKey) != null) {
//            log.info("🚀 Cache HIT! Returning cached data for ASIN: {} and country: {}", asin, country);
//        }
//    }

    @Before("execution(* *.getProductDetails(String, String))")
    public void logCacheAccess(JoinPoint joinPoint) {
        log.info("Cache check has been triggered for ASIN API request");
        Object[] args = joinPoint.getArgs();
        String asin = (String) args[0];
        String country = (String) args[1];

        String cacheKey = asin + "_" + country;
        Cache cache = cacheManager.getCache("asinCache");
        if (cache != null && cache.get(cacheKey) != null) {
            log.info("🚀 Cache HIT! Returning cached data for ASIN: {} and country: {}", asin, country);
        } else {
            log.info("💾 Cache MISS! Will make API request for ASIN: {} and country: {}", asin, country);
        }
    }
}