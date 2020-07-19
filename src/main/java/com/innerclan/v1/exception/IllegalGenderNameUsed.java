package com.innerclan.v1.exception;

public class IllegalGenderNameUsed  extends  RuntimeException{

    String message;

    public IllegalGenderNameUsed(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
