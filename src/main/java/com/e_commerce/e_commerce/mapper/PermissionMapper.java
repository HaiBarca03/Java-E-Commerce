package com.e_commerce.e_commerce.mapper;

import org.mapstruct.Mapper;

import com.e_commerce.e_commerce.dto.requests.PermissionRequest;
import com.e_commerce.e_commerce.dto.response.PermissionResponse;
import com.e_commerce.e_commerce.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
