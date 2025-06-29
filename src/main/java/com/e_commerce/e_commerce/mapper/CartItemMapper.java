package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.CartItemRequest;
import com.e_commerce.e_commerce.dto.response.CartItemResponse;
import com.e_commerce.e_commerce.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemRequest request);
    CartItemResponse toResponse(CartItem entity);
}
