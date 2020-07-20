package com.innerclan.v1.exception;

public class PromoNotFoundException extends  RuntimeException{

    String message;

    public PromoNotFoundException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
