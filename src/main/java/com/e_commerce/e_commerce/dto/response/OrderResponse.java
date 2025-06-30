package com.e_commerce.e_commerce.dto.response;

import com.e_commerce.e_commerce.constant.OrderStatus;
import com.e_commerce.e_commerce.constant.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String userId;
    String addressId;
    BigDecimal totalAmount;
    OrderStatus status;
    PaymentMethod paymentMethod;
    String note;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<OrderItemResponse> orderItems;
}
