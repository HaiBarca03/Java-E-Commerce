package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponsValidateRequest {
    String code;
    BigDecimal orderValue;
    String userId;
}
