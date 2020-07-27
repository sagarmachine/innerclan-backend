package com.innerclan.v1.service;

import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import org.springframework.http.ResponseEntity;
import  java.util.*;
public interface IOrderService {

    void completeOrder(String orderId,String txnId, String paymentMethod);

    void orderFailed(String orderId);

    void createOrder(Client client, double total, double promoDiscount, String promoUsed,Address address, Set<CartItem>colors,String orderId);

     void updateOrderStatus(long id, String orderStatus);

     ResponseEntity<?> addQuery(long id, String query , String email );

}
