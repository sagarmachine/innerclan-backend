package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class OrderNotFoundException extends  RuntimeException{

    String message;

    public OrderNotFoundException(String message){
        this.message=message;
    }


}
