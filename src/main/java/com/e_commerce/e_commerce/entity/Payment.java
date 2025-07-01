package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import com.e_commerce.e_commerce.constant.PaymentMethod;
import com.e_commerce.e_commerce.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @Column(nullable = false)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentStatus status;

    @Column(name = "transaction_id")
    String transactionId;

    @Column(name = "payment_time")
    Instant paymentTime;

    @Column(name = "provider")
    String provider;
}
