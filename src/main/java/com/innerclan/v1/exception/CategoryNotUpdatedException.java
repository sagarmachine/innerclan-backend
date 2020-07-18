package com.innerclan.v1.exception;

public class CategoryNotUpdatedException extends  RuntimeException{

    String message;

    public CategoryNotUpdatedException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
