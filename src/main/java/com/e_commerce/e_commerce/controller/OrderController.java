package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.OrderRequest;
import com.e_commerce.e_commerce.dto.response.OrderResponse;
import com.e_commerce.e_commerce.dto.response.PagedResponse;
import com.e_commerce.e_commerce.entity.Order;
import com.e_commerce.e_commerce.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing order")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request) {
        OrderResponse createdOrder = orderService.createOrder(request);

        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(1000)
                .message("Đặt hàng thành công")
                .result(createdOrder)
                .build());
    }

    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUser() {
        List<OrderResponse> orders = orderService.getOrdersByUser();

        ApiResponse<List<OrderResponse>> response = ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng của bạn thành công")
                .result(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagedResponse<OrderResponse>>> getAllOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<OrderResponse> orders = orderService.getAllOrders(status, userId, page, size);

        ApiResponse<PagedResponse<OrderResponse>> response = ApiResponse.<PagedResponse<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng thành công")
                .result(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetail(@PathVariable String orderId) {
        OrderResponse order = orderService.getOrderDetail(orderId);

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Lấy thông tin đơn hàng thành công")
                .result(order)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}")
    @PostAuthorize("returnObject.body.result.userId == authentication.token.claims['id']")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable String orderId,
            @RequestBody OrderRequest request
    ) {
        OrderResponse updatedOrder = orderService.updateOrder(orderId, request);

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Cập nhật đơn hàng thành công")
                .result(updatedOrder)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    @PostAuthorize("returnObject.body.result.userId == authentication.token.claims['id']")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Huỷ đơn hàng thành công")
                .build();

        return ResponseEntity.ok(response);
    }

}
