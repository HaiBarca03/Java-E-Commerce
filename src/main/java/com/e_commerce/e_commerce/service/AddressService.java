package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.dto.requests.AddressRequest;
import com.e_commerce.e_commerce.dto.response.AddressResponse;
import com.e_commerce.e_commerce.entity.Address;
import com.e_commerce.e_commerce.entity.User;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.AddressMapper;
import com.e_commerce.e_commerce.repository.AddressRepository;
import com.e_commerce.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {

    AddressRepository addressRepository;
    UserRepository userRepository;
    AddressMapper addressMapper;

    public AddressResponse createAddress(AddressRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Address address = addressMapper.toEntity(request, user);
        Address saved = addressRepository.save(address);

        return addressMapper.toResponse(saved);
    }

    public List<AddressResponse> getMyAddresses() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(addressMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteAddress(String id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        addressRepository.delete(address);
    }

    public AddressResponse updateAddress(String id, AddressRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        address.setRecipientName(request.getRecipientName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setStreet(request.getStreet());
        address.setWard(request.getWard());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setNote(request.getNote());
        address.setDefault(request.isDefault());

        Address updated = addressRepository.save(address);
        return addressMapper.toResponse(updated);
    }
}
