package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.e_commerce.e_commerce.constant.Gender;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    String username;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(nullable = false)
    String password;

    String fullName;
    String phoneNumber;

    @Column(columnDefinition = "TEXT")
    String address;

    @Enumerated(EnumType.STRING)
    Gender gender;

    LocalDate dateOfBirth;

    String avatarUrl;

    @Column(columnDefinition = "TEXT")
    String bio;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    Set<Role> roles;
}
