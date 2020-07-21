package com.innerclan.v1.exception;

public class ClientAlreadyExsitException extends  RuntimeException{

    String message;

    public ClientAlreadyExsitException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
