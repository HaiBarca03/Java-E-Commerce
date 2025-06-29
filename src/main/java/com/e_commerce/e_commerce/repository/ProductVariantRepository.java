package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
    boolean existsBySku(String sku);
    Optional<ProductVariant> findBySku(String sku);
    @Query("""
        SELECT v FROM ProductVariant v 
        WHERE v.product.id = :productId 
        AND (:color IS NULL OR v.color = :color)
        AND (:size IS NULL OR v.size = :size)
        AND (:type IS NULL OR v.type = :type)
    """)
    List<ProductVariant> findByProductIdAndFilters(
            @Param("productId") String productId,
            @Param("color") String color,
            @Param("size") String size,
            @Param("type") String type
    );
}
