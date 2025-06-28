package com.e_commerce.e_commerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.e_commerce.e_commerce.dto.requests.RoleRequest;
import com.e_commerce.e_commerce.dto.response.RoleResponse;
import com.e_commerce.e_commerce.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
