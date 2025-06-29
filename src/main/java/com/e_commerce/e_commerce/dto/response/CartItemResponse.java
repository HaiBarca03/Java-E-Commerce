package com.e_commerce.e_commerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
