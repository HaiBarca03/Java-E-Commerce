package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.CategoryRequest;
import com.e_commerce.e_commerce.dto.requests.CreateCategoryRequest;
import com.e_commerce.e_commerce.dto.response.CategoryResponse;
import com.e_commerce.e_commerce.entity.Category;
import com.e_commerce.e_commerce.entity.Product;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CreateCategoryRequest request);

    @Mapping(target = "productIds", expression = "java(mapProductIds(category))")
    CategoryResponse toCategoryResponse(Category category);

    default Set<String> mapProductIds(Category category) {
        if (category.getProducts() == null) return null;
        return category.getProducts()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
    }
}
