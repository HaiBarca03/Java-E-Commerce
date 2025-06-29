package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, String> {
    List<Wishlist> findByUserId(String userId);
    Optional<Wishlist> findByUserIdAndProductVariantId(String userId, String variantId);
    void deleteByUserIdAndProductVariantId(String userId, String variantId);
    List<Wishlist> findAllByProductVariantId(String productVariantId);
    Long countByProductVariantId(String productVariantId);
}
