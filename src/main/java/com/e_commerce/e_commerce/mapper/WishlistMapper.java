package com.e_commerce.e_commerce.mapper;

import com.e_commerce.e_commerce.dto.requests.WishlistRequest;
import com.e_commerce.e_commerce.dto.response.WishlistResponse;
import com.e_commerce.e_commerce.entity.Wishlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    Wishlist toWishlist(WishlistRequest request);

    WishlistResponse toWishlistResponse(Wishlist wishlist);
}
