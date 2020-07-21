package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class CategoryAlreadyExistException extends  RuntimeException{

    String message;

    public CategoryAlreadyExistException(String message){
        this.message=message;
    }


}
