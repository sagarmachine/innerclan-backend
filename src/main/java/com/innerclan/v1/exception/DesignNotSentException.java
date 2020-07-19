package com.innerclan.v1.exception;

public class DesignNotSentException extends  RuntimeException{

    String message;

    public DesignNotSentException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
