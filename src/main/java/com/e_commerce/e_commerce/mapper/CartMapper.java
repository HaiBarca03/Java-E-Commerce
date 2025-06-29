package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.CartRequest;
import com.e_commerce.e_commerce.dto.response.CartResponse;
import com.e_commerce.e_commerce.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
    Cart toEntity(CartRequest request);
    CartResponse toResponse(Cart cart);
}
