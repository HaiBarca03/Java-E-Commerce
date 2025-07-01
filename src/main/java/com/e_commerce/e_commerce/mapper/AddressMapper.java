package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.AddressRequest;
import com.e_commerce.e_commerce.dto.response.AddressResponse;
import com.e_commerce.e_commerce.entity.Address;
import com.e_commerce.e_commerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class AddressMapper {

    public Address toEntity(AddressRequest request, User user) {
        return Address.builder()
                .recipientName(request.getRecipientName())
                .phoneNumber(request.getPhoneNumber())
                .street(request.getStreet())
                .ward(request.getWard())
                .district(request.getDistrict())
                .city(request.getCity())
                .note(request.getNote())
                .isDefault(request.isDefault())
                .user(user)
                .build();
    }

    public AddressResponse toResponse(Address address) {
        return AddressResponse.builder()
                .recipientName(address.getRecipientName())
                .phoneNumber(address.getPhoneNumber())
                .street(address.getStreet())
                .ward(address.getWard())
                .district(address.getDistrict())
                .city(address.getCity())
                .note(address.getNote())
                .isDefault(address.isDefault())
                .build();
    }
}
