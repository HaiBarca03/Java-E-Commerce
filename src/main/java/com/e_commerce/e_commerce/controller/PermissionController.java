package com.e_commerce.e_commerce.controller;

import com.e_commerce.e_commerce.dto.requests.PermissionRequest;
import com.e_commerce.e_commerce.dto.response.PermissionResponse;
import com.e_commerce.e_commerce.service.PermissionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission Management", description = "APIs for managing permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        PermissionResponse response = permissionService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @DeleteMapping("/{permissionName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePermission(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ResponseEntity.noContent().build();
    }
}
