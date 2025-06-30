package com.e_commerce.e_commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1009, "Role not found", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1009, "Category not found", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1010, "Category existed", HttpStatus.BAD_REQUEST),
    SKU_EXISTED(1011, "Sku existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1012, "Product not found", HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANTS_NOT_FOUND(1013, "Product variants not found", HttpStatus.BAD_REQUEST),
    WISHLIST_EXISTED(1014, "Already in wishlist", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(1015, "Address not found", HttpStatus.BAD_REQUEST),
    CODE_NOT_FOUND(1016, "Code not found", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(1017, "Cart not found", HttpStatus.BAD_REQUEST ),
    CART_ALREADY_EXISTS(1018,"Cart by user existed", HttpStatus.BAD_REQUEST ),
    ORDER_NOT_FOUND(1019,"Order not found", HttpStatus.BAD_REQUEST  ),
    ORDER_CANNOT_BE_UPDATED(1020, "Order can not update", HttpStatus.BAD_REQUEST ),
    ORDER_CANNOT_BE_CANCELLED(1021, "Order can not delete", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
