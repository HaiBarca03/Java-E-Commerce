package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.CartItemRequest;
import com.e_commerce.e_commerce.dto.requests.CartRequest;
import com.e_commerce.e_commerce.dto.response.CartResponse;
import com.e_commerce.e_commerce.entity.Cart;
import com.e_commerce.e_commerce.entity.CartItem;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.CartMapper;
import com.e_commerce.e_commerce.repository.CartRepository;
import com.e_commerce.e_commerce.repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class CartService {

    CartRepository cartRepository;
    ProductVariantRepository productVariantRepository;
    CartMapper cartMapper;

    public ResponseEntity<ApiResponse<CartResponse>> createCart(CartRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        for (var item : request.getItems()) {
            boolean exists = productVariantRepository.existsById(item.getProductId());
            if (!exists) {
                throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
            }
        }

        if (cart == null) {
            cart = cartMapper.toEntity(request);
            cart.setUserId(userId);

            if (cart.getItems() != null) {
                for (CartItem item : cart.getItems()) {
                    item.setCart(cart);
                }
            }

        } else {
            for (CartItemRequest itemRequest : request.getItems()) {
                CartItem item = new CartItem();
                item.setProductId(itemRequest.getProductId());
                item.setQuantity(itemRequest.getQuantity());
                item.setPrice(itemRequest.getPrice());
                item.setCart(cart);
                cart.getItems().add(item);
            }
        }

        cartRepository.save(cart);

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .message(cart.getId() == null ? "Cart created" : "Cart updated")
                        .result(cartMapper.toResponse(cart))
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<CartResponse>> getCartByUser(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .result(cartMapper.toResponse(cart))
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<CartResponse>> clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        cart.getItems().clear();
        cartRepository.save(cart);

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .message("Cart cleared")
                        .result(cartMapper.toResponse(cart))
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<CartResponse>> removeItemFromCart(String productId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!removed) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        cartRepository.save(cart);

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .message("Item removed from cart")
                        .result(cartMapper.toResponse(cart))
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<CartResponse>> updateItemQuantity(String productId, int quantity) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        var optionalItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (optionalItem.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        optionalItem.get().setQuantity(quantity);

        cartRepository.save(cart);

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .message("Item quantity updated successfully")
                        .result(cartMapper.toResponse(cart))
                        .build()
        );
    }
}
