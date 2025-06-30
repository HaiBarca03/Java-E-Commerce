package com.e_commerce.e_commerce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CartResponse {
    String id;
    String userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<CartItemResponse> items;
}
