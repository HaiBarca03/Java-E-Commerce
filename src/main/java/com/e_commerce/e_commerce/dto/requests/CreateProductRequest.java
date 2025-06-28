package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductRequest {
    String name;
    String description;
    BigDecimal price;
    BigDecimal discountPrice;
    Integer quantity;
    String sku;
    String brand;
    List<String> images;
    Set<String> categoryIds;
}
