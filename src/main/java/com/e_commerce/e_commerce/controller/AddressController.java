package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.AddressRequest;
import com.e_commerce.e_commerce.dto.response.AddressResponse;
import com.e_commerce.e_commerce.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Address Management", description = "APIs for managing addresses")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)

public class AddressController {

    AddressService addressService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(@RequestBody AddressRequest request) {
        AddressResponse response = addressService.createAddress(request);
        return ResponseEntity.ok(
                ApiResponse.<AddressResponse>builder()
                        .code(1000)
                        .message("Tạo địa chỉ thành công")
                        .result(response)
                        .build()
        );
    }

    @GetMapping("/my-address")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getMyAddresses() {
        List<AddressResponse> addresses = addressService.getMyAddresses();
        return ResponseEntity.ok(
                ApiResponse.<List<AddressResponse>>builder()
                        .code(1000)
                        .message("Lấy danh sách địa chỉ thành công")
                        .result(addresses)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable String id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(1000)
                        .message("Xoá địa chỉ thành công")
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(@PathVariable String id,
                                                                      @RequestBody AddressRequest request) {
        AddressResponse response = addressService.updateAddress(id, request);
        return ResponseEntity.ok(
                ApiResponse.<AddressResponse>builder()
                        .code(1000)
                        .message("Cập nhật địa chỉ thành công")
                        .result(response)
                        .build()
        );
    }
}
