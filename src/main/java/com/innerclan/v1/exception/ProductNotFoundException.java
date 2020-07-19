package com.innerclan.v1.exception;

public class ProductNotFoundException extends  RuntimeException{

    String message;

    public ProductNotFoundException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
