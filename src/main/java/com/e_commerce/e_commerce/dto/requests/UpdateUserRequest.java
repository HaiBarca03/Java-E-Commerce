package com.e_commerce.e_commerce.dto.requests;

import com.e_commerce.e_commerce.constant.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String fullName;
    String phoneNumber;
    String address;
    Gender gender;
    LocalDate dateOfBirth;
    String avatarUrl;
    String bio;
}
