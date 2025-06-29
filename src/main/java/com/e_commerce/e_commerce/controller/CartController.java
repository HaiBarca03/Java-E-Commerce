package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.CartRequest;
import com.e_commerce.e_commerce.dto.response.CartResponse;
import com.e_commerce.e_commerce.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Tag(name = "Cart Management", description = "APIs for managing cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CartResponse>> createCart(@RequestBody CartRequest request) {
        return cartService.createCart(request);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.token.claims['id']")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUser(@PathVariable String userId) {
        return cartService.getCartByUser(userId);
    }

    @DeleteMapping("/{productId}")
    @PostAuthorize("returnObject.body.result.userId == authentication.token.claims['id']")
    public ResponseEntity<ApiResponse<CartResponse>> removeItem(@PathVariable String productId) {
        return cartService.removeItemFromCart(productId);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.token.claims['id']")
    public ResponseEntity<ApiResponse<CartResponse>> clearCart(@PathVariable String userId) {
        return cartService.clearCart(userId);
    }

    @PutMapping("/{productId}")
    @PostAuthorize("returnObject.body.result.userId == authentication.token.claims['id']")
    public ResponseEntity<ApiResponse<CartResponse>> updateItemQuantity(
            @PathVariable String productId,
            @RequestParam int quantity) {
        return cartService.updateItemQuantity(productId, quantity);
    }
}
