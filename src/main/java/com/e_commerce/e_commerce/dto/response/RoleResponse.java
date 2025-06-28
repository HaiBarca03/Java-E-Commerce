package com.e_commerce.e_commerce.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponse {
    private String id;
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
}
