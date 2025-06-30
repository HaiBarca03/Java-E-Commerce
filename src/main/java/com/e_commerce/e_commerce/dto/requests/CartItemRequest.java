package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CartItemRequest {
    String productId;
    Integer quantity;
    BigDecimal price;
}
