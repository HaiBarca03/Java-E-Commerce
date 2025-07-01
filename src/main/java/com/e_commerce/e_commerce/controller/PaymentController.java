package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.CreateMomoPaymentRequest;
import com.e_commerce.e_commerce.dto.requests.PaymentRequest;
import com.e_commerce.e_commerce.dto.response.PaymentResponse;
import com.e_commerce.e_commerce.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment Management", description = "APIs for managing payment")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    @PostMapping("/momo")
    public ResponseEntity<PaymentResponse> createMomoPayment(@RequestBody CreateMomoPaymentRequest request) {
        PaymentResponse response = paymentService.createPaymentMomo(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ipn")
    public ResponseEntity<String> handleMomoIPN(@RequestBody Map<String, Object> payload) {
        try {
            paymentService.handleIPN(payload);
            return ResponseEntity.ok("0"); // success code for MoMo
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1");
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> responses = paymentService.getAllPayments();
        return ResponseEntity.ok(
                ApiResponse.<List<PaymentResponse>>builder()
                        .code(1000)
                        .message("Danh sách thanh toán")
                        .result(responses)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable String id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .code(1000)
                        .message("Chi tiết thanh toán")
                        .result(response)
                        .build()
        );
    }
}
