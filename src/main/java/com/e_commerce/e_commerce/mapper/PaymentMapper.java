package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.PaymentRequest;
import com.e_commerce.e_commerce.dto.response.PaymentResponse;
import com.e_commerce.e_commerce.entity.Order;
import com.e_commerce.e_commerce.entity.Payment;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final OrderRepository orderRepository;

    public Payment toEntity(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        return Payment.builder()
                .order(order)
                .amount(request.getAmount())
                .method(request.getMethod())
                .status(request.getStatus())
                .transactionId(request.getTransactionId())
                .paymentTime(request.getPaymentTime())
                .provider(request.getProvider())
                .build();
    }

    public PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .paymentTime(payment.getPaymentTime())
                .provider(payment.getProvider())
                .build();
    }
}
