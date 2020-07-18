package com.innerclan.v1.exception;

public class CategoryNotSavedException extends  RuntimeException{

    String message;

    public CategoryNotSavedException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
