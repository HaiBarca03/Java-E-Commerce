package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.CategoryRequest;
import com.e_commerce.e_commerce.dto.requests.CreateCategoryRequest;
import com.e_commerce.e_commerce.dto.response.CategoryResponse;
import com.e_commerce.e_commerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category CRUD operations")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new category")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryResponse result = categoryService.createCategory(request);
        return ApiResponse.<CategoryResponse>builder()
                .result(result)
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category by ID")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String id, @RequestBody CategoryRequest request) {
        CategoryResponse result = categoryService.updateCategory(id, request);
        return ApiResponse.<CategoryResponse>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category by ID")
    public ApiResponse<String> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable String id) {
        CategoryResponse result = categoryService.getCategoryById(id);
        return ApiResponse.<CategoryResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> result = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(result)
                .build();
    }
}
