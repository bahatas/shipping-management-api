package com.shippingmanagementapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shippingmanagementapi.dto.CreateOrderRequest;


import com.shippingmanagementapi.dto.OrderDTO;
import com.shippingmanagementapi.model.Orders;
import com.shippingmanagementapi.repository.OrderRepository;
import com.shippingmanagementapi.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MapperUtil mapperUtil;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository orderRepository, UserService userService, MapperUtil mapperUtil, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public OrderDTO createOrder(String username, CreateOrderRequest request) throws JsonProcessingException {

        String receivedOrder = objectMapper.writeValueAsString(request);
        log.info("Received order to be created : {}", receivedOrder);
        Orders order = new Orders();
        order.setUser(userService.findUserIdByEmail(username));
        order.setOrderNumber(generateOrderNumber());
        order.setStoreName(request.getStoreName());
        order.setStatus("pending");
        order.setProductName(request.getProductName());
        order.setAsinCode(request.getAsinCode());
        order.setCountry(request.getCountry());
        order.setOrderAmount(request.getOrderAmount());
        order.setAdditionalFee(request.getAdditionalFee());
        order.setTotalAmount(request.getOrderAmount().add(request.getAdditionalFee()));
        order.setOrderDate(LocalDateTime.now());
        OrderDTO storedOrder = convertToDTO(orderRepository.save(order));
        log.info("Order stored successfully : {}" , storedOrder);
        return storedOrder;
    }

    public List<OrderDTO> getUserOrders(String username) {
        Long userId = userService.findByUsername(username).getId();
        List<OrderDTO> orders = orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("Orders has been fetched for user ");
        return orders;
    }

    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow();
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderDTO convertToDTO(Orders order) {
        OrderDTO dto = OrderDTO.builder().build();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStoreName(order.getStoreName());
        dto.setStatus(order.getStatus());
        dto.setProductName(order.getProductName());
        dto.setAsinCode(order.getAsinCode());
        dto.setCountry(order.getCountry());
        dto.setOrderAmount(order.getOrderAmount());
        dto.setAdditionalFee(order.getAdditionalFee());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setTrackingLink(order.getTrackingLink());
        dto.setOrderDate(order.getOrderDate());
        dto.setApprovedAt(order.getApprovedAt());
        return dto;
    }
//    private OrdersDto convertToDTO(Orders order) {
//        OrdersDto dto = OrdersDto.builder()
//                .id(order.getId())
//                .build();
//        //dto.setId(order.getId());
//        dto.setOrderNumber(order.getOrderNumber());
//        dto.setStoreName(order.getStoreName());
//        dto.setStatus(order.getStatus());
//        dto.setProductName(order.getProductName());
//        dto.setAsinCode(order.getAsinCode());
//        dto.setCountry(order.getCountry());
//        dto.setOrderAmount(order.getOrderAmount());
//        dto.setAdditionalFee(order.getAdditionalFee());
//        dto.setTotalAmount(order.getTotalAmount());
//        dto.setTrackingLink(order.getTrackingLink());
//        dto.setOrderDate(order.getOrderDate());
//        dto.setApprovedAt(order.getApprovedAt());
//        return dto;
//    }


}