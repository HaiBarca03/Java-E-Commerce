package com.e_commerce.e_commerce.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MomoPaymentResponse {
    private int resultCode;
    private String message;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
}
