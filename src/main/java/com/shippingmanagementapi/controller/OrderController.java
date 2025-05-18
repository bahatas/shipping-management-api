package com.shippingmanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shippingmanagementapi.dto.CreateOrderRequest;
import com.shippingmanagementapi.dto.OrderDTO;
import com.shippingmanagementapi.repository.OrderRepository;
import com.shippingmanagementapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;


    public OrderController(OrderService orderService,
                           OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("username") String email, @RequestBody CreateOrderRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.createOrder(email, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(@RequestHeader("username") String username) {
        log.info("Received get order of users for user :{}", username);
        List<OrderDTO> userOrders = orderService.getUserOrders(username);
        log.info("Fetched total order : {}",userOrders.size());
        return ResponseEntity.ok(orderService.getUserOrders(username));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        log.info("Order Id requested {}",orderId);
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
} 