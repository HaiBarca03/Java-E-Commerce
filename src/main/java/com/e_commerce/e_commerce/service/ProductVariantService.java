package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.dto.requests.ProductVariantRequest;
import com.e_commerce.e_commerce.dto.response.ProductVariantResponse;
import com.e_commerce.e_commerce.entity.Product;
import com.e_commerce.e_commerce.entity.ProductVariant;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.ProductVariantMapper;
import com.e_commerce.e_commerce.repository.ProductRepository;
import com.e_commerce.e_commerce.repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {

    ProductVariantRepository variantRepository;
    ProductRepository productRepository;
    ProductVariantMapper variantMapper;

    public ProductVariantResponse createVariant(ProductVariantRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (variantRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.SKU_EXISTED);
        }

        ProductVariant variant = variantMapper.toEntity(request, product);
        variantRepository.save(variant);
        return variantMapper.toResponse(variant);
    }

    public ProductVariantResponse getDetail(String id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANTS_NOT_FOUND));
        return variantMapper.toResponse(variant);
    }

    public ProductVariantResponse getBySku(String sku) {
        ProductVariant variant = variantRepository.findBySku(sku)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANTS_NOT_FOUND));
        return variantMapper.toResponse(variant);
    }

    public List<ProductVariantResponse> getByProductIdAndFilters(String productId, String color, String size, String type) {
        List<ProductVariant> variants = variantRepository.findByProductIdAndFilters(productId, color, size, type);
        return variants.stream()
                .map(variantMapper::toResponse)
                .toList();
    }

    public void delete(String id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANTS_NOT_FOUND));
        variantRepository.delete(variant);
    }

    public ProductVariantResponse update(String id, ProductVariantRequest request) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANTS_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        variant.setProduct(product);
        variant.setColor(request.getColor());
        variant.setSize(request.getSize());
        variant.setType(request.getType());
        variant.setPrice(request.getPrice());
        variant.setQuantity(request.getQuantity());
        variant.setSku(request.getSku());
        variant.setImage(request.getImage());

        variantRepository.save(variant);

        return variantMapper.toResponse(variant);
    }

}
