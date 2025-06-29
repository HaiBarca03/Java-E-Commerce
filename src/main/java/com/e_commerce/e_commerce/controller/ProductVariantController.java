package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.dto.requests.ProductVariantRequest;
import com.e_commerce.e_commerce.dto.response.ProductVariantResponse;
import com.e_commerce.e_commerce.service.ProductVariantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
@Tag(name = "Product Variants Management", description = "APIs for managing product variants")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductVariantController {

    ProductVariantService productVariantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductVariantResponse> createVariant(@RequestBody ProductVariantRequest request) {
        ProductVariantResponse response = productVariantService.createVariant(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponse> getDetail(@PathVariable String id) {
        ProductVariantResponse response = productVariantService.getDetail(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductVariantResponse> getBySku(@PathVariable String sku) {
        ProductVariantResponse response = productVariantService.getBySku(sku);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/variants")
    public ResponseEntity<List<ProductVariantResponse>> getByProductIdAndFilters(
            @PathVariable String productId,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String type
    ) {
        List<ProductVariantResponse> responses = productVariantService.getByProductIdAndFilters(productId, color, size, type);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productVariantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductVariantResponse> update(
            @PathVariable String id,
            @RequestBody ProductVariantRequest request
    ) {
        ProductVariantResponse response = productVariantService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
