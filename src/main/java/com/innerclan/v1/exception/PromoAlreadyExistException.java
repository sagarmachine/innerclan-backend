package com.innerclan.v1.exception;

public class PromoAlreadyExistException extends  RuntimeException{

    String message;

    public PromoAlreadyExistException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
