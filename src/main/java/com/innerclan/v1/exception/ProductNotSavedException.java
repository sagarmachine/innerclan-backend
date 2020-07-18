package com.innerclan.v1.exception;

public class ProductNotSavedException extends  RuntimeException{

    String message;

    public ProductNotSavedException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
