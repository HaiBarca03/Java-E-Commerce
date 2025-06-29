package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.CouponRequest;
import com.e_commerce.e_commerce.dto.requests.CouponsValidateRequest;
import com.e_commerce.e_commerce.dto.response.CouponsResponse;
import com.e_commerce.e_commerce.service.CouponsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/coupons")
@Tag(name = "Coupons Management", description = "APIs for managing coupons")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)

public class CouponsController {

    CouponsService couponsService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean isActive
    ) {
        return couponsService.getAll(isActive, page, size);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CouponsResponse>> createCoupon(@RequestBody CouponRequest request) {
        ApiResponse<CouponsResponse> response = couponsService.createCoupon(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable String id, @RequestBody CouponRequest request) {
        return couponsService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {
        return couponsService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getOne(@PathVariable String id) {
        return couponsService.getOne(id);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<?>> toggle(@PathVariable String id) {
        return couponsService.toggle(id);
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<?>> validate(@RequestBody CouponsValidateRequest request) {
        return couponsService.validate(request);
    }
}
