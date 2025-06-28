package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.CreateProductRequest;
import com.e_commerce.e_commerce.dto.response.ProductResponse;
import com.e_commerce.e_commerce.entity.Category;
import com.e_commerce.e_commerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(CreateProductRequest request);

    @Mapping(source = "categories", target = "categories", qualifiedByName = "categoryNames")
    ProductResponse toProductResponse(Product product);

    @Named("categoryNames")
    static List<String> mapCategoriesToNames(Set<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
