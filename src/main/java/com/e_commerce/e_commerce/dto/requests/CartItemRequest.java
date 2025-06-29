package com.e_commerce.e_commerce.dto.requests;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
