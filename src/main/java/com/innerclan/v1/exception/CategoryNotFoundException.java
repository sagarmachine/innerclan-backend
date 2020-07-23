package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends  RuntimeException{

    String message;

    public CategoryNotFoundException(String message){
        this.message=message;
    }


}
