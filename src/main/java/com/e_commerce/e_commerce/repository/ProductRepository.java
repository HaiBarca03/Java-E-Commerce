package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> , JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);
    boolean existsBySku(String sku);
    Optional<Product> findById(String id);
    Optional<Product> findBySlug(String slug);
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.slug = :slug")
    Page<Product> findAllByCategorySlug(@Param("slug") String slug, Pageable pageable);
    void deleteById(String id);
    boolean existsById(String id);
}
