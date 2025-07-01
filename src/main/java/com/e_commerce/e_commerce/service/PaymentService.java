package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.constant.OrderStatus;
import com.e_commerce.e_commerce.constant.PaymentMethod;
import com.e_commerce.e_commerce.constant.PaymentStatus;
import com.e_commerce.e_commerce.dto.requests.CreateMomoPaymentRequest;
import com.e_commerce.e_commerce.dto.requests.PaymentRequest;
import com.e_commerce.e_commerce.dto.response.PaymentResponse;
import com.e_commerce.e_commerce.entity.Order;
import com.e_commerce.e_commerce.entity.Payment;
import com.e_commerce.e_commerce.mapper.PaymentMapper;
import com.e_commerce.e_commerce.repository.OrderRepository;
import com.e_commerce.e_commerce.repository.PaymentRepository;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PaymentService {

    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;
    OrderRepository orderRepository;

    private static final String ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
    private static final String PARTNER_CODE = "MOMO";
    private static final String ACCESS_KEY = "F8BBA842ECF85";
    private static final String SECRET_KEY = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    private static final String REDIRECT_URL = "http://localhost:3000/payment-success";
    private static final String IPN_URL = "http://localhost:8080/e-commerce/api/payments/ipn";

    public PaymentResponse createPaymentMomo(CreateMomoPaymentRequest request) {
        try {
            String requestId = UUID.randomUUID().toString();
            String orderId = request.getOrderId();
            String orderInfo = "Thanh toán đơn hàng";
            String requestType = "captureWallet";
            String extraData = "";
            String lang = "vi"; // Language for the request

            // Create the raw data map with parameters in alphabetical order
            Map<String, String> rawData = new TreeMap<>(); // TreeMap ensures alphabetical order
            rawData.put("accessKey", ACCESS_KEY);
            rawData.put("amount", request.getAmount().toPlainString());
            rawData.put("extraData", extraData);
            rawData.put("ipnUrl", IPN_URL);
            rawData.put("orderId", orderId);
            rawData.put("orderInfo", orderInfo);
            rawData.put("partnerCode", PARTNER_CODE);
            rawData.put("redirectUrl", REDIRECT_URL);
            rawData.put("requestId", requestId);
            rawData.put("requestType", requestType);

            String rawHash = rawData.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            String signature = hmacSHA256(rawHash, SECRET_KEY);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("partnerCode", PARTNER_CODE);
            requestBody.put("partnerName", "MoMo Test");
            requestBody.put("storeId", "Store123");
            requestBody.put("requestId", requestId);
            requestBody.put("amount", request.getAmount().toPlainString());
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", orderInfo);
            requestBody.put("redirectUrl", REDIRECT_URL);
            requestBody.put("ipnUrl", IPN_URL);
            requestBody.put("lang", lang);
            requestBody.put("extraData", extraData);
            requestBody.put("requestType", requestType);
            requestBody.put("signature", signature);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(ENDPOINT, HttpMethod.POST, entity, String.class);

            // Process the response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String payUrl = extractPayUrl(response.getBody());
                Order order = orderRepository.findById(request.getOrderId())
                        .orElseThrow(() -> new RuntimeException("Order not found"));

                Payment payment = new Payment();
                payment.setOrder(order);
                payment.setAmount(request.getAmount());
                payment.setMethod(PaymentMethod.MOMO);
                payment.setStatus(PaymentStatus.PENDING);
                payment.setTransactionId(orderId);
                payment.setProvider("MoMo");
                payment.setPaymentTime(Instant.now());

                Payment saved = paymentRepository.save(payment);

                PaymentResponse paymentResponse = paymentMapper.toResponse(saved);
                paymentResponse.setPaymentUrl(payUrl);
                return paymentResponse;
            } else {
                throw new RuntimeException("Không thể tạo thanh toán MoMo: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo thanh toán: " + e.getMessage(), e);
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String extractPayUrl(String json) {
        System.out.println("MoMo Response: " + json);
        int start = json.indexOf("\"payUrl\":\"") + 10;
        int end = json.indexOf("\"", start);
        return json.substring(start, end).replace("\\/", "/");
    }

    public void handleIPN(Map<String, Object> payload) {
        String orderId = (String) payload.get("orderId");
        String resultCode = String.valueOf(payload.get("resultCode"));
        String transactionId = (String) payload.get("transId");

        if ("0".equals(resultCode)) {
            // Thành công → cập nhật trạng thái payment và order
            Payment payment = paymentRepository.findByTransactionId(orderId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy payment với orderId " + orderId));
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            // Có thể cập nhật đơn hàng, ví dụ:
            Order order = payment.getOrder();
            order.setStatus(OrderStatus.SHIPPED); // nếu có enum OrderStatus
            orderRepository.save(order);
        } else {
            // Thanh toán thất bại → update trạng thái nếu cần
            System.out.println("MoMo IPN báo lỗi: " + resultCode);
        }
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    public PaymentResponse getPaymentById(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }
}
