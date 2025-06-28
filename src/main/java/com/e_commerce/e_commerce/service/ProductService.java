package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.dto.requests.CreateProductRequest;
import com.e_commerce.e_commerce.dto.requests.ProductFilterRequest;
import com.e_commerce.e_commerce.dto.requests.UpdateProductRequest;
import com.e_commerce.e_commerce.dto.response.PagedResponse;
import com.e_commerce.e_commerce.dto.response.ProductResponse;
import com.e_commerce.e_commerce.entity.Category;
import com.e_commerce.e_commerce.entity.Product;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.ProductMapper;
import com.e_commerce.e_commerce.repository.CategoryRepository;
import com.e_commerce.e_commerce.repository.ProductRepository;
import com.e_commerce.e_commerce.utils.PageUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.e_commerce.e_commerce.utils.SlugUtil.toSlug;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.SKU_EXISTED);
        }

        Product product = productMapper.toProduct(request);

        Set<Category> categories = request.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toSet());

        product.setCategories(categories);
        product.setSlug(toSlug(request.getName()));

        return productMapper.toProductResponse(productRepository.save(product));
    }

    public ProductResponse getProductDetail(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(product);
    }

    public ProductResponse getProductDetailBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product);
    }

    public Page<ProductResponse> getProductsByCategorySlug(String slug, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> products = productRepository.findAllByCategorySlug(slug, pageable);
        return products.map(productMapper::toProductResponse);
    }

    public ProductResponse updateProduct(String productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Update basic fields
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setQuantity(request.getQuantity());
        product.setSku(request.getSku());
        product.setBrand(request.getBrand());
        product.setImages(request.getImages());
        product.setSlug(toSlug(request.getName()));
        // Update categories intelligently
        Set<Category> currentCategories = product.getCategories();
        Set<Category> newCategories = request.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toSet());

        if (!currentCategories.equals(newCategories)) {
            product.setCategories(newCategories);
        }

        return productMapper.toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(productId);
    }

    public PagedResponse<ProductResponse> getAllProducts(ProductFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }
            if (request.getRating() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), request.getRating()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> productPage = productRepository.findAll(spec, pageable);
        Page<ProductResponse> responsePage = productPage.map(productMapper::toProductResponse);

        return PageUtils.buildPagedResponse(responsePage);
    }

    public Page<ProductResponse> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Product> spec = (root, query, builder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return builder.conjunction();
            }

            String likePattern = "%" + keyword.toLowerCase() + "%";
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.like(builder.lower(root.get("name")), likePattern));
            predicates.add(builder.like(root.get("description"), "%" + keyword + "%"));
            predicates.add(builder.like(builder.lower(root.get("brand")), likePattern));

            return builder.or(predicates.toArray(new Predicate[0]));
        };

        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::toProductResponse);
    }
}
