package com.shippingmanagementapi.repository;

import com.shippingmanagementapi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
    List<Orders> findByUserIdAndStatus(Long userId, String status);
    Orders findByOrderNumber(String orderNumber);
} 