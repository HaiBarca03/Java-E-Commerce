package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMomoPaymentRequest {
    private BigDecimal amount;
    private String orderId;
}
