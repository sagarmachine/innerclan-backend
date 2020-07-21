package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class SubsciberAlreadyExistException extends  RuntimeException{

    String message;

    public SubsciberAlreadyExistException(String message){
        this.message=message;
    }


}
