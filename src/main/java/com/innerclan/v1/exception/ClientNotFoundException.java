package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class ClientNotFoundException extends  RuntimeException{

    String message;

    public ClientNotFoundException(String message){
        this.message=message;
    }


}
