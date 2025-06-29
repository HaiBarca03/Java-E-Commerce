package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import com.e_commerce.e_commerce.constant.CouponsType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "coupons")
public class Coupons extends BaseEntity {

    @Column(unique = true, nullable = false)
    String code;

    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    CouponsType discountType;

    @Column(name = "discount_value", nullable = false)
    BigDecimal discountValue;

    @Column(name = "max_discount_amount")
    BigDecimal maxDiscountAmount;

    @Column(name = "min_order_value")
    BigDecimal minOrderValue;

    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

    @Column(name = "usage_limit")
    Integer usageLimit;

    @Column(name = "used_count")
    Integer usedCount = 0;

    @Column(name = "is_active")
    Boolean isActive = true;

    @ElementCollection
    @CollectionTable(name = "discount_code_user_usage", joinColumns = @JoinColumn(name = "discount_code_id"))
    @Column(name = "user_id")
    Set<String> userIdsUsed;
}
