package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class ImageNotSavedException extends  RuntimeException{

    String message;

    public ImageNotSavedException(String message){
        this.message=message;
    }


}
