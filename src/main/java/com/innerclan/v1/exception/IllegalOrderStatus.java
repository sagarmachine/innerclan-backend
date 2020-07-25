package com.innerclan.v1.exception;

public class IllegalOrderStatus extends  RuntimeException{

    String message;

    public IllegalOrderStatus(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

