package com.e_commerce.e_commerce.dto.requests;

import lombok.Data;

import java.util.Set;

@Data
public class RoleRequest {
    private String name;
    private String description;
    private Set<String> permissionIds; // list of permission IDs
}
