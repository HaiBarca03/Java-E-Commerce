package com.e_commerce.e_commerce.dto.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RegisterRequest {
    String username;
    String email;
    String password;
}
