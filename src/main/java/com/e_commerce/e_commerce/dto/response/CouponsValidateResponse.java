package com.e_commerce.e_commerce.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CouponsValidateResponse {
    boolean valid;
    BigDecimal discountAmount;
    BigDecimal finalAmount;
}
