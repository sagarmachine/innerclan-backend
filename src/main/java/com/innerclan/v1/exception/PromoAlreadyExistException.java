package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class PromoAlreadyExistException extends  RuntimeException{

    String message;

    public PromoAlreadyExistException(String message){
        this.message=message;
    }


}
