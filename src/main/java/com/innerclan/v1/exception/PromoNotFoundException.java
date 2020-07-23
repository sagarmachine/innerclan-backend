package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class PromoNotFoundException extends  RuntimeException{

    String message;

    public PromoNotFoundException(String message){
        this.message=message;
    }


}
