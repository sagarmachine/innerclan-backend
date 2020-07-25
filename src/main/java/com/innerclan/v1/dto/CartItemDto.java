package com.innerclan.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    ClientProductView clientProductView;

    String color ;

    String size;

    long quantity;

    double totalPrice;


    public CartItemDto(ClientProductView clientProductView, long quantity, double totalPrice) {

        this.clientProductView=clientProductView;
        this.quantity=quantity;
        this.totalPrice=totalPrice;
    }
}

