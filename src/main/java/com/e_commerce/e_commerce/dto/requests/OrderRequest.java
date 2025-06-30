package com.e_commerce.e_commerce.dto.requests;

import com.e_commerce.e_commerce.constant.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderRequest {
    String addressId;
    PaymentMethod paymentMethod;;
    String note;
    List<OrderItemRequest> items;
}
