package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class CartItemNotFoundException extends  RuntimeException{

    String message;

    public CartItemNotFoundException(String message){
        this.message=message;
    }


}
