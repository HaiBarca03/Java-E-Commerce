package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.OrderItemRequest;
import com.e_commerce.e_commerce.dto.response.OrderItemResponse;
import com.e_commerce.e_commerce.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequest request);
    OrderItemResponse toResponse(OrderItem entity);
}
