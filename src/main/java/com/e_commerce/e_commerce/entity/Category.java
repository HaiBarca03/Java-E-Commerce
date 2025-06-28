package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.Table;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "categories")
public class Category  extends BaseEntity {

    @Column(nullable = false, unique = true)
    String name;

    String description;

    String slug;

    @ManyToMany(mappedBy = "categories")
    Set<Product> products;
}
