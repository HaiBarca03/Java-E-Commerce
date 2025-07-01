package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.dto.requests.ReviewRequest;
import com.e_commerce.e_commerce.dto.response.PagedResponse;
import com.e_commerce.e_commerce.dto.response.ReviewResponse;
import com.e_commerce.e_commerce.entity.*;
import com.e_commerce.e_commerce.constant.OrderStatus;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.ReviewMapper;
import com.e_commerce.e_commerce.repository.*;
import com.e_commerce.e_commerce.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ReviewService {

    ReviewRepository reviewRepository;
    OrderItemRepository orderItemRepository;
    UserRepository userRepository;
    ReviewMapper reviewMapper;
    ProductVariantRepository productVariantRepository;

    public ReviewResponse createReview( ReviewRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        if (reviewRepository.existsByUserIdAndOrderItemId(userId, request.getOrderItemId())) {
            throw new AppException(ErrorCode.REVIEW_ALREADY);
        }

        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!orderItem.getOrder().getStatus().equals(OrderStatus.SHIPPED)) {
            throw new AppException(ErrorCode.REVIEW_CAN_NOT_CREATE);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        ProductVariant productVariant = productVariantRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANTS_NOT_FOUND));

        Product product = productVariant.getProduct();

        Review review = Review.builder()
                .user(user)
                .orderItem(orderItem)
                .product(product)
                .productVariant(productVariant)
                .rating(request.getRating())
                .comment(request.getComment())
                .imageUrl(request.getImageUrl())
                .build();

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    public PagedResponse<ReviewResponse> getReviewsByProductId(String productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var reviewPage = reviewRepository.findAllByProductId(productId, pageable);
        var mappedPage = reviewPage.map(reviewMapper::toResponse);
        return PageUtils.buildPagedResponse(mappedPage);
    }

    public ReviewResponse updateReview(String reviewId, ReviewRequest request) {
        String userId = getCurrentUserId();

        Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setImageUrl(request.getImageUrl());

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    public void deleteReview(String reviewId) {
        String userId = getCurrentUserId();

        Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        reviewRepository.delete(review);
    }

    private String getCurrentUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaim("id");
    }
}

