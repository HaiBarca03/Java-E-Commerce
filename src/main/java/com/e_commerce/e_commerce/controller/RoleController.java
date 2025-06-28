package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.dto.requests.RoleRequest;
import com.e_commerce.e_commerce.dto.response.RoleResponse;
import com.e_commerce.e_commerce.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Tag(name = "Role Management", description = "APIs for managing roles and their permissions")
public class RoleController {

    RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new role")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all roles")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        return ResponseEntity.ok(ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get role by ID")
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder()
                .result(roleService.getById(id))
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update role by ID")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable String id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder()
                .result(roleService.update(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete role by ID")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
