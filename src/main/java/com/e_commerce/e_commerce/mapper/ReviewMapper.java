package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.response.ReviewResponse;
import com.e_commerce.e_commerce.dto.requests.RegisterRequest;
import com.e_commerce.e_commerce.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(RegisterRequest request);

    @Mapping(target = "productName", source = "productVariant.product.name")
    @Mapping(target = "variantSku", source = "productVariant.sku")
    ReviewResponse toResponse(Review review);
}
