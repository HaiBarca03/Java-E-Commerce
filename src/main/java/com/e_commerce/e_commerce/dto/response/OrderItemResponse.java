package com.e_commerce.e_commerce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderItemResponse {
    String productId;
    Integer quantity;
    BigDecimal price;
}
