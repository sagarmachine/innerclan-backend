package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends  RuntimeException{

    String message;

    public AuthenticationException(String message){
        this.message=message;
    }


}
