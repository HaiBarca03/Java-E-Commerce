package com.e_commerce.e_commerce.dto.requests;

import com.e_commerce.e_commerce.constant.PaymentMethod;
import com.e_commerce.e_commerce.constant.PaymentStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String transactionId;
    private Instant paymentTime;
    private String provider;
}
