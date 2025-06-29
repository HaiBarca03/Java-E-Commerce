package com.e_commerce.e_commerce.dto.requests;

import com.e_commerce.e_commerce.constant.CouponsType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponRequest {
    String description;
    CouponsType discountType;
    BigDecimal discountValue;
    BigDecimal maxDiscountAmount;
    BigDecimal minOrderValue;
    LocalDateTime expirationDate;
    Integer usageLimit;
}
