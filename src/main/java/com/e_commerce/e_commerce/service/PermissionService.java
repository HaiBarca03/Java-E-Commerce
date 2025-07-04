package com.e_commerce.e_commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.dto.requests.PermissionRequest;
import com.e_commerce.e_commerce.dto.response.PermissionResponse;
import com.e_commerce.e_commerce.entity.Permission;
import com.e_commerce.e_commerce.mapper.PermissionMapper;
import com.e_commerce.e_commerce.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
