package com.innerclan.v1.exception;

public class SubsciberAlreadyExistException extends  RuntimeException{

    String message;

    public SubsciberAlreadyExistException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
