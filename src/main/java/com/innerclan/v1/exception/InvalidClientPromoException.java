package com.innerclan.v1.exception;

public class InvalidClientPromoException extends  RuntimeException{

    String message;

    public InvalidClientPromoException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
