package com.shippingmanagementapi.controller;

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
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("user-id") Long userId, @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(@RequestHeader("user-id") Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        log.info("Order Id requested {}",orderId);
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
} 