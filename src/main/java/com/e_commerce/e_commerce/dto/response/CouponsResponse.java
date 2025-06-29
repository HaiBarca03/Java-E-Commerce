package com.e_commerce.e_commerce.dto.response;

import com.e_commerce.e_commerce.constant.CouponsType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponsResponse {
    String code;
    String description;
    CouponsType discountType;
    BigDecimal discountValue;
    BigDecimal maxDiscountAmount;
    BigDecimal minOrderValue;
    LocalDateTime expirationDate;
    Integer usageLimit;
    Integer usedCount;
    Boolean isActive;
}
