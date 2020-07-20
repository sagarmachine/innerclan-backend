package com.innerclan.v1.exception;

public class ClientNotFoundException extends  RuntimeException{

    String message;

    public ClientNotFoundException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
