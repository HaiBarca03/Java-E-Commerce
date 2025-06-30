package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.OrderRequest;
import com.e_commerce.e_commerce.dto.response.OrderResponse;
import com.e_commerce.e_commerce.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequest request);
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "address.id", target = "addressId")
    OrderResponse toResponse(Order order);
}
