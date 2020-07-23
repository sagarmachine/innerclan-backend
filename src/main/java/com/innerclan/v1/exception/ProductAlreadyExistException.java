package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class ProductAlreadyExistException extends  RuntimeException{

    String message;

    public ProductAlreadyExistException(String message){
        this.message=message;
    }


}
