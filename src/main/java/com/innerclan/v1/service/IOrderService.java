package com.innerclan.v1.service;

import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Color;
import org.springframework.http.ResponseEntity;
import  java.util.*;
public interface IOrderService {

    void completeOrder(String email);

    void createOrder(Client client, double total, double promoDiscount, Address address, Set<CartItemDto>colors,String orderId);

     void updateOrderStatus(long id, String orderStatus);

     ResponseEntity<?> addQuery(long id, String query , String email );

}
