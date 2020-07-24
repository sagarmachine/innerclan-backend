package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class IllegalForgotPasswordRequestException extends  RuntimeException{

    String message;

    public IllegalForgotPasswordRequestException(String message){
        this.message=message;
    }


}
