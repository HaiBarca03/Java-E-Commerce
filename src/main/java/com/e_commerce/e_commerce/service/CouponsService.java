package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.constant.CouponsType;
import com.e_commerce.e_commerce.dto.requests.CouponRequest;
import com.e_commerce.e_commerce.dto.requests.CouponsValidateRequest;
import com.e_commerce.e_commerce.dto.response.CouponsResponse;
import com.e_commerce.e_commerce.dto.response.CouponsValidateResponse;
import com.e_commerce.e_commerce.entity.Coupons;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.CouponsMapper;
import com.e_commerce.e_commerce.repository.CouponsRepository;
import com.e_commerce.e_commerce.utils.CodeGeneratorUtil;
import com.e_commerce.e_commerce.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class CouponsService {

    CouponsRepository couponsRepository;
    CouponsMapper couponsMapper;

    public ApiResponse<CouponsResponse> createCoupon(CouponRequest request) {
        String code = generateUniqueCode();

        Coupons coupon = couponsMapper.toEntity(request, code);
        Coupons saved = couponsRepository.save(coupon);

        CouponsResponse response = couponsMapper.toResponse(saved);
        return ApiResponse.<CouponsResponse>builder()
                .message("Tạo mã giảm giá thành công")
                .result(response)
                .build();
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = CodeGeneratorUtil.generateRandomCode(8);
        } while (couponsRepository.existsByCode(code));
        return code;
    }

    public ResponseEntity<ApiResponse<?>> update(String id, CouponRequest request) {
        Coupons coupons = couponsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));

        if (request.getDescription() != null) {
            coupons.setDescription(request.getDescription());
        }
        if (request.getDiscountType() != null) {
            coupons.setDiscountType(request.getDiscountType());
        }
        if (request.getDiscountValue() != null) {
            coupons.setDiscountValue(request.getDiscountValue());
        }
        if (request.getMaxDiscountAmount() != null) {
            coupons.setMaxDiscountAmount(request.getMaxDiscountAmount());
        }
        if (request.getMinOrderValue() != null) {
            coupons.setMinOrderValue(request.getMinOrderValue());
        }
        if (request.getExpirationDate() != null) {
            coupons.setExpirationDate(request.getExpirationDate());
        }
        if (request.getUsageLimit() != null) {
            coupons.setUsageLimit(request.getUsageLimit());
        }

        couponsRepository.save(coupons);

        CouponsResponse response = couponsMapper.toResponse(coupons);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Cập nhật thành công")
                .result(response)
                .build());
    }

    public ResponseEntity<ApiResponse<?>> delete(String id) {
        Coupons coupons = couponsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));

        couponsRepository.delete(coupons);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Xoá thành công")
                .build());
    }

    public ResponseEntity<ApiResponse<?>> getOne(String id) {
        Coupons coupons = couponsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));

        return ResponseEntity.ok(ApiResponse.builder()
                .result(couponsMapper.toResponse(coupons))
                .build());
    }

    public ResponseEntity<ApiResponse<?>> toggle(String id) {
        Coupons coupons = couponsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));

        coupons.setIsActive(!coupons.getIsActive());
        couponsRepository.save(coupons);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Đổi trạng thái thành công")
                .result(couponsMapper.toResponse(coupons))
                .build());
    }

    public ResponseEntity<ApiResponse<?>> getAll(Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Coupons> resultPage = (isActive == null)
                ? couponsRepository.findAll(pageable)
                : couponsRepository.findByIsActive(isActive, pageable);

        Page<CouponsResponse> mappedPage = resultPage.map(couponsMapper::toResponse);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Lấy danh sách mã giảm giá thành công")
                .result(PageUtils.buildPagedResponse(mappedPage))
                .build());
    }

    public ResponseEntity<ApiResponse<?>> validate(CouponsValidateRequest request) {
        Coupons coupons = couponsRepository.findByCode(request.getCode())
                .orElseThrow(() -> new RuntimeException("Mã không tồn tại"));

        if (!Boolean.TRUE.equals(coupons.getIsActive()))
            return ResponseEntity.badRequest().body(ApiResponse.builder().message("Mã đã bị vô hiệu hoá").build());

        if (coupons.getExpirationDate() != null && coupons.getExpirationDate().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body(ApiResponse.builder().message("Mã đã hết hạn").build());

        BigDecimal orderValue = request.getOrderValue();
        if (coupons.getMinOrderValue() != null && orderValue.compareTo(coupons.getMinOrderValue()) < 0)
            return ResponseEntity.badRequest().body(ApiResponse.builder().message("Đơn hàng chưa đủ giá trị").build());

        if (coupons.getUsageLimit() != null && coupons.getUsedCount() >= coupons.getUsageLimit())
            return ResponseEntity.badRequest().body(ApiResponse.builder().message("Mã đã hết lượt dùng").build());

        if (coupons.getUserIdsUsed() != null && coupons.getUserIdsUsed().contains(request.getUserId()))
            return ResponseEntity.badRequest().body(ApiResponse.builder().message("Bạn đã sử dụng mã này rồi").build());

        BigDecimal discount;
        if (coupons.getDiscountType() == CouponsType.PERCENT) {
            discount = orderValue.multiply(coupons.getDiscountValue()).divide(BigDecimal.valueOf(100));
        } else {
            discount = coupons.getDiscountValue();
        }

        if (coupons.getMaxDiscountAmount() != null) {
            discount = discount.min(coupons.getMaxDiscountAmount());
        }

        BigDecimal finalAmount = orderValue.subtract(discount);

        CouponsValidateResponse result = CouponsValidateResponse.builder()
                .valid(true)
                .discountAmount(discount)
                .finalAmount(finalAmount)
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Mã hợp lệ")
                .result(result)
                .build());
    }
}
