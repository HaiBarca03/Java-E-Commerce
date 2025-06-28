package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import com.e_commerce.e_commerce.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String slug;

    @Lob
    String description;

    @Column(nullable = false)
    BigDecimal price;

    BigDecimal discountPrice;

    @Column(nullable = false)
    Integer quantity;

    @Column(unique = true)
    String sku;

    @Enumerated(EnumType.STRING)
    ProductStatus status;

    String brand;

    Double rating;

    Integer sold;

    @ElementCollection
    List<String> images;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductVariant> variants;
}
