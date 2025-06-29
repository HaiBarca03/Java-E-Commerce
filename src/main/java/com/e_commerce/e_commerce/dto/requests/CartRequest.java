package com.e_commerce.e_commerce.dto.requests;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {
    private List<CartItemRequest> items;
}
