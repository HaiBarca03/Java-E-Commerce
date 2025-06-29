package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.CouponRequest;
import com.e_commerce.e_commerce.dto.response.CouponsResponse;
import com.e_commerce.e_commerce.entity.Coupons;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CouponsMapper {

    public Coupons toEntity(CouponRequest request, String generatedCode) {
        return Coupons.builder()
                .code(generatedCode)
                .description(request.getDescription())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .maxDiscountAmount(request.getMaxDiscountAmount())
                .minOrderValue(request.getMinOrderValue())
                .expirationDate(request.getExpirationDate())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .isActive(true)
                .userIdsUsed(new HashSet<>())
                .build();
    }

    public CouponsResponse toResponse(Coupons coupons) {
        return CouponsResponse.builder()
                .code(coupons.getCode())
                .description(coupons.getDescription())
                .discountType(coupons.getDiscountType())
                .discountValue(coupons.getDiscountValue())
                .maxDiscountAmount(coupons.getMaxDiscountAmount())
                .minOrderValue(coupons.getMinOrderValue())
                .expirationDate(coupons.getExpirationDate())
                .usageLimit(coupons.getUsageLimit())
                .usedCount(coupons.getUsedCount())
                .isActive(coupons.getIsActive())
                .build();
    }
}
