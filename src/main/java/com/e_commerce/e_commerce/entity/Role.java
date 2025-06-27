package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    String name; // e.g., ROLE_ADMIN, ROLE_USER

    String description;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<Permission> permissions;
}
