package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.dto.requests.WishlistRequest;
import com.e_commerce.e_commerce.dto.response.ProductVariantResponse;
import com.e_commerce.e_commerce.dto.response.WishlistByProductResponse;
import com.e_commerce.e_commerce.dto.response.WishlistResponse;
import com.e_commerce.e_commerce.dto.response.WishlistUserResponse;
import com.e_commerce.e_commerce.entity.ProductVariant;
import com.e_commerce.e_commerce.entity.User;
import com.e_commerce.e_commerce.entity.Wishlist;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.repository.ProductVariantRepository;
import com.e_commerce.e_commerce.repository.UserRepository;
import com.e_commerce.e_commerce.repository.WishlistRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WishlistService {

    WishlistRepository wishlistRepository;
    UserRepository userRepository;
    ProductVariantRepository productVariantRepository;

    public WishlistResponse addToWishlist(WishlistRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANTS_NOT_FOUND));

        if (wishlistRepository.findByUserIdAndProductVariantId(user.getId(), variant.getId()).isPresent()) {
            throw new AppException(ErrorCode.WISHLIST_EXISTED);
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .productVariant(variant)
                .build();

        Wishlist saved = wishlistRepository.save(wishlist);

        return WishlistResponse.builder()
                .id(saved.getId())
                .userId(saved.getUser().getId())
                .productVariantId(saved.getProductVariant().getId())
                .productVariant(ProductVariantResponse.builder()
                        .id(saved.getProductVariant().getId())
                        .sku(saved.getProductVariant().getSku())
                        .price(saved.getProductVariant().getPrice())
                        .color(saved.getProductVariant().getColor())
                        .size(saved.getProductVariant().getSize())
                        .type(saved.getProductVariant().getType())
                        .build())
                .build();
    }

    public List<WishlistResponse> getUserWishlist(String userId) {
        return wishlistRepository.findByUserId(userId).stream()
                .map(w -> WishlistResponse.builder()
                        .id(w.getId())
                        .userId(w.getUser().getId())
                        .productVariantId(w.getProductVariant().getId())
                        .productVariant(ProductVariantResponse.builder()
                                .id(w.getProductVariant().getId())
                                .sku(w.getProductVariant().getSku())
                                .price(w.getProductVariant().getPrice())
                                .color(w.getProductVariant().getColor())
                                .size(w.getProductVariant().getSize())
                                .type(w.getProductVariant().getType())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public List<WishlistResponse> getMyWishlist() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        return wishlistRepository.findByUserId(userId).stream()
                .map(w -> WishlistResponse.builder()
                        .id(w.getId())
                        .userId(w.getUser().getId())
                        .productVariantId(w.getProductVariant().getId())
                        .productVariant(ProductVariantResponse.builder()
                                .id(w.getProductVariant().getId())
                                .sku(w.getProductVariant().getSku())
                                .price(w.getProductVariant().getPrice())
                                .color(w.getProductVariant().getColor())
                                .size(w.getProductVariant().getSize())
                                .type(w.getProductVariant().getType())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public void removeFromWishlist(String userId, String variantId) {
        wishlistRepository.deleteByUserIdAndProductVariantId(userId, variantId);
    }

    public WishlistByProductResponse getWishlistByProductVariant(String productVariantId) {
        List<Wishlist> wishlists = wishlistRepository.findAllByProductVariantId(productVariantId);
        Long count = wishlistRepository.countByProductVariantId(productVariantId);

        List<WishlistUserResponse> users = wishlists.stream().map(w -> {
            return WishlistUserResponse.builder()
                    .userId(w.getUser().getId())
                    .username(w.getUser().getUsername())
                    .email(w.getUser().getEmail())
                    .build();
        }).toList();

        return WishlistByProductResponse.builder()
                .productVariantId(productVariantId)
                .wishlistCount(count)
                .users(users)
                .build();
    }
}
