package com.e_commerce.e_commerce.dto.response;

import com.e_commerce.e_commerce.constant.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String token;
    boolean authenticated;

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
    Set<String> roles; // chỉ tên roles
}
