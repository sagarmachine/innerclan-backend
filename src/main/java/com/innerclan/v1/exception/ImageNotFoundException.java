package com.innerclan.v1.exception;

public class ImageNotFoundException extends  RuntimeException{

    String message;

    public ImageNotFoundException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
