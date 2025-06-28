package com.e_commerce.e_commerce.dto.response;

import com.e_commerce.e_commerce.constant.Gender;
import com.e_commerce.e_commerce.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String fullName;
    String phoneNumber;
    String address;
    Gender gender;
    LocalDate dateOfBirth;
    String avatarUrl;
    String bio;
    boolean enabled;
    Set<Role> roles;
}
