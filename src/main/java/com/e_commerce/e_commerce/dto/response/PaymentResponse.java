package com.e_commerce.e_commerce.dto.response;

import com.e_commerce.e_commerce.constant.PaymentMethod;
import com.e_commerce.e_commerce.constant.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class PaymentResponse {
    String id;
    String orderId;
    BigDecimal amount;
    PaymentMethod method;
    PaymentStatus status;
    String transactionId;
    Instant paymentTime;
    String provider;
    String paymentUrl;
}
