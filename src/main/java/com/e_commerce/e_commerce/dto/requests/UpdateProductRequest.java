package com.e_commerce.e_commerce.dto.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UpdateProductRequest {
    String name;
    String description;
    BigDecimal price;
    BigDecimal discountPrice;
    Integer quantity;
    String sku;
    String brand;
    List<String> images;
    List<String> categoryIds;
}
