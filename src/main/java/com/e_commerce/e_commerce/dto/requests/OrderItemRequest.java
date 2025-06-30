package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderItemRequest {
    String productId;
    Integer quantity;
    BigDecimal price;
}
