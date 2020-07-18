package com.innerclan.v1.exception;

public class CategoryAlreadyExistException extends  RuntimeException{

    String message;

    public CategoryAlreadyExistException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
