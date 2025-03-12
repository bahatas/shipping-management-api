package com.shippingmanagementapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
@Getter
@Setter
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String storeName;
    private String status;
    private String productName;
    private String asinCode;
    private String country;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "additional_fee")
    private BigDecimal additionalFee;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "tracking_link")
    private String trackingLink;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Orders order = (Orders) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
