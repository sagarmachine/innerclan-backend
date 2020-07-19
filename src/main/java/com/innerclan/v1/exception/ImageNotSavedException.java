package com.innerclan.v1.exception;

public class ImageNotSavedException extends  RuntimeException{

    String message;

    public ImageNotSavedException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
