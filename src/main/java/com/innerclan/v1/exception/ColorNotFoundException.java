package com.innerclan.v1.exception;

public class ColorNotFoundException extends  RuntimeException{

    String message;

    public ColorNotFoundException(String message){
        this.message=message;
    }


}
