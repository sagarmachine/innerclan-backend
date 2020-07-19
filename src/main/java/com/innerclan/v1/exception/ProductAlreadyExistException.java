package com.innerclan.v1.exception;

public class ProductAlreadyExistException extends  RuntimeException{

    String message;

    public ProductAlreadyExistException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
