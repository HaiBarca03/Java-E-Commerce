package com.e_commerce.e_commerce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class WishlistResponse {
    String id;
    String userId;
    String productVariantId;
    ProductVariantResponse productVariant;
}
