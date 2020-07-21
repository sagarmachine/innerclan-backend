package com.innerclan.v1.exception;


import lombok.Getter;

@Getter
public class IllegalGenderNameUsedException  extends  RuntimeException{

    String message;

    public IllegalGenderNameUsedException(String message){
        this.message=message;
    }


}
