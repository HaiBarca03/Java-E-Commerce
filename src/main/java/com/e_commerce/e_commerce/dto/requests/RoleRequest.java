package com.e_commerce.e_commerce.dto.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RoleRequest {
    String name;
    String description;
    Set<String> permissionIds; // list of permission IDs
}
