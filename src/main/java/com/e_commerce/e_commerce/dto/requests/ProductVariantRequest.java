package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantRequest {
    String productId;
    String color;
    String size;
    String type;
    BigDecimal price;
    Integer quantity;
    String sku;
    String image;
}
