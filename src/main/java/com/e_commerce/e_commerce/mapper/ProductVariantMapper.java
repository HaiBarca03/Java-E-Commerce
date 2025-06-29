package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.ProductVariantRequest;
import com.e_commerce.e_commerce.dto.response.ProductVariantResponse;
import com.e_commerce.e_commerce.entity.Product;
import com.e_commerce.e_commerce.entity.ProductVariant;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantMapper {

    public ProductVariant toEntity(ProductVariantRequest request, Product product) {
        return ProductVariant.builder()
                .product(product)
                .color(request.getColor())
                .size(request.getSize())
                .type(request.getType())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .sku(request.getSku())
                .image(request.getImage())
                .build();
    }

    public ProductVariantResponse toResponse(ProductVariant variant) {
        return ProductVariantResponse.builder()
                .id(variant.getId())
                .productId(variant.getProduct().getId())
                .color(variant.getColor())
                .size(variant.getSize())
                .type(variant.getType())
                .price(variant.getPrice())
                .quantity(variant.getQuantity())
                .sku(variant.getSku())
                .image(variant.getImage())
                .build();
    }
}
