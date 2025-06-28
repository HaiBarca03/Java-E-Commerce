package com.e_commerce.e_commerce.utils;

import com.e_commerce.e_commerce.dto.response.PagedResponse;
import org.springframework.data.domain.Page;

public class PageUtils {
    public static <T> PagedResponse<T> buildPagedResponse(Page<T> pageData) {
        return PagedResponse.<T>builder()
                .content(pageData.getContent())
                .currentPage(pageData.getNumber())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .pageSize(pageData.getSize())
                .build();
    }
}
