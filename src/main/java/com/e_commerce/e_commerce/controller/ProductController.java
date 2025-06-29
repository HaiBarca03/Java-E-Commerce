package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.dto.requests.CreateProductRequest;
import com.e_commerce.e_commerce.dto.requests.ProductFilterRequest;
import com.e_commerce.e_commerce.dto.requests.UpdateProductRequest;
import com.e_commerce.e_commerce.dto.response.PagedResponse;
import com.e_commerce.e_commerce.dto.response.ProductResponse;
import com.e_commerce.e_commerce.service.ProductService;
import com.e_commerce.e_commerce.utils.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ProductResponse getProductDetail(@PathVariable String id) {
        return productService.getProductDetail(id);
    }

    @GetMapping("/slug/{slug}")
    public ProductResponse getProductDetailBySlug(@PathVariable String slug) {
        return productService.getProductDetailBySlug(slug);
    }

    @GetMapping("/category/{slug}")
    public ResponseEntity<PagedResponse<ProductResponse>> getProductsByCategory(
            @PathVariable String slug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse> result = productService.getProductsByCategorySlug(slug, page, size);
        PagedResponse<ProductResponse> response = PageUtils.buildPagedResponse(result);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable String id,
                                           @RequestBody @Valid UpdateProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Double rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ProductFilterRequest filterRequest = new ProductFilterRequest();
        filterRequest.setName(name);
        filterRequest.setMinPrice(minPrice);
        filterRequest.setMaxPrice(maxPrice);
        filterRequest.setRating(rating);
        filterRequest.setPage(page);
        filterRequest.setSize(size);

        return ResponseEntity.ok(productService.getAllProducts(filterRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedResponse<ProductResponse>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse> result = productService.searchProducts(keyword, page, size);
        PagedResponse<ProductResponse> response = PageUtils.buildPagedResponse(result);
        return ResponseEntity.ok(response);
    }
}
