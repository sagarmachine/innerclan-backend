package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class ClientAlreadyExsitException extends  RuntimeException{

    String message;

    public ClientAlreadyExsitException(String message){
        this.message=message;
    }


}
