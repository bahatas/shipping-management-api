package com.shippingmanagementapi.repository;

import com.shippingmanagementapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
} 