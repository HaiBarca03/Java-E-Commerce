package com.e_commerce.e_commerce.controller;
import com.e_commerce.e_commerce.dto.response.LoginResponse;
import com.e_commerce.e_commerce.dto.response.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.e_commerce.dto.requests.*;
import com.e_commerce.e_commerce.dto.response.AuthenticationResponse;
import com.e_commerce.e_commerce.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Management", description = "APIs for managing auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Operation(summary = "Authenticate", description = "Authenticate user and return token with profile")
    @PostMapping("/authenticate")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @Operation(summary = "User login", description = "Login user and return token with profile")
    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.login(request);
        return ApiResponse.<LoginResponse>builder().result(result).build();
    }

    @Operation(summary = "User register", description = "Register user and return token with profile")
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest request) {
        String token = authenticationService.register(request);
        return ApiResponse.<String>builder().result(token).build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthenticationResponse token = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(token)
                .build();
    }
}
