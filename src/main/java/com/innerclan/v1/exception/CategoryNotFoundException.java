package com.innerclan.v1.exception;

public class CategoryNotFoundException extends  RuntimeException{

    String message;

    public CategoryNotFoundException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
