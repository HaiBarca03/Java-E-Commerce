package com.e_commerce.e_commerce.mapper;

import org.mapstruct.Mapper;

import com.e_commerce.e_commerce.dto.response.UserResponse;
import com.e_commerce.e_commerce.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
