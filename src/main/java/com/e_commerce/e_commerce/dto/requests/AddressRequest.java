package com.e_commerce.e_commerce.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {
    String recipientName;
    String phoneNumber;
    String street;
    String ward;
    String district;
    String city;
    String note;
    boolean isDefault;
}
