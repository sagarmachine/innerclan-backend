package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class DesignNotFoundException extends  RuntimeException{

    String message;

    public DesignNotFoundException(String message){
        this.message=message;
    }

//    @Override
//    public String getMessage() {
//        return this.message;
//    }
}
