package com.innerclan.v1.service;

import com.innerclan.v1.dto.AddToCartDto;
import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.CartItem;

import java.util.List;

public interface ICartItemService {
    void addCartItem(String email,AddToCartDto addToCartDto);

    double addCartItems(String email, List<AddToCartDto> addToCartDtoList);
    List<CartItemDto> getCartItems(String email);

    void deleteCartItem(String email, long productId);

    void deleteAllCartItems(String email);


    double getCartTotal(String email);
}
