package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class ProductNotSavedException extends  RuntimeException{

    String message;

    public ProductNotSavedException(String message){
        this.message=message;
    }


}
