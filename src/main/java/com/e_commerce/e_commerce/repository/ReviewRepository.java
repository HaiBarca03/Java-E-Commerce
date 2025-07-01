package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByUserIdAndOrderItemId(String userId, String orderItemId);
    Page<Review> findAllByProductId(String productId, Pageable pageable);
    Optional<Review> findByIdAndUserId(String reviewId, String userId);
}
