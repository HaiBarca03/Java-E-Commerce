package com.e_commerce.e_commerce.dto.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductFilterRequest {
    String name;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Double rating;
    int page = 0;
    int size = 10;
}
