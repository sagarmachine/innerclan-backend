package com.innerclan.v1.exception;

public class IllegalGenderNameUsedException  extends  RuntimeException{

    String message;

    public IllegalGenderNameUsedException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
