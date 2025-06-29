package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.WishlistRequest;
import com.e_commerce.e_commerce.dto.response.WishlistByProductResponse;
import com.e_commerce.e_commerce.dto.response.WishlistResponse;
import com.e_commerce.e_commerce.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/wishlists")
@Tag(name = "Wishlist Management", description = "APIs for managing wishlist")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)

public class WishlistController {

    WishlistService wishlistService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<WishlistResponse>> addToWishlist(@RequestBody WishlistRequest request) {
        WishlistResponse response = wishlistService.addToWishlist(request);
        return ResponseEntity.ok(ApiResponse.<WishlistResponse>builder()
                .result(response)
                .message("Added to wishlist successfully")
                .build());
    }

    @GetMapping("/my-wish-list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getMyWishlist() {
        List<WishlistResponse> responseList = wishlistService.getMyWishlist();
        return ResponseEntity.ok(ApiResponse.<List<WishlistResponse>>builder()
                .result(responseList)
                .message("Fetched my wishlist successfully")
                .build());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getUserWishlist(@PathVariable String userId) {
        List<WishlistResponse> responseList = wishlistService.getUserWishlist(userId);
        return ResponseEntity.ok(ApiResponse.<List<WishlistResponse>>builder()
                .result(responseList)
                .message("Fetched wishlist by user successfully")
                .build());
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(
            @RequestParam String userId,
            @RequestParam String productVariantId) {
        wishlistService.removeFromWishlist(userId, productVariantId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Removed from wishlist successfully")
                .build());
    }

    @GetMapping("/product-variant/{variantId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<WishlistByProductResponse>> getWishlistByProductVariant(@PathVariable String variantId) {
        WishlistByProductResponse response = wishlistService.getWishlistByProductVariant(variantId);
        return ResponseEntity.ok(ApiResponse.<WishlistByProductResponse>builder()
                .result(response)
                .message("Fetched wishlist by product variant successfully")
                .build());
    }
}
