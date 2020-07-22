package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.CartItem;

import java.util.List;

public interface ICartItemService {
    void addCartItem(String email, long productId);

    List<CartItemDto> getCartItems(String email);

    void deleteCartItem(String email, long productId);
}
