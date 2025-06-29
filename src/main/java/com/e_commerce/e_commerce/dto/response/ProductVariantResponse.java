package com.e_commerce.e_commerce.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {
    String id;
    String productId;
    String color;
    String size;
    String type;
    BigDecimal price;
    Integer quantity;
    String sku;
    String image;
}
