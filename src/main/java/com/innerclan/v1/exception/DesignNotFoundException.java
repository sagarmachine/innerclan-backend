package com.innerclan.v1.exception;

public class DesignNotFoundException extends  RuntimeException{

    String message;

    public DesignNotFoundException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
