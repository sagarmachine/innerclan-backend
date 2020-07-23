package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class DesignNotSentException extends  RuntimeException{

    String message;

    public DesignNotSentException(String message){
        this.message=message;
    }


}
