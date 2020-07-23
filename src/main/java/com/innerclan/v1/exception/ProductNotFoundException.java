package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class ProductNotFoundException extends  RuntimeException{

    String message;

    public ProductNotFoundException(String message){
        this.message=message;
    }

    public String getMessage() {
        return this.message;
    }

}
