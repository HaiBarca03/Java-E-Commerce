package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_variants", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "color", "size", "type"})
})
public class ProductVariant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(nullable = false)
    String color;

    @Column(nullable = false)
    String size;

    @Column(nullable = false)
    String type;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false, unique = true)
    String sku;

    String image;
}
