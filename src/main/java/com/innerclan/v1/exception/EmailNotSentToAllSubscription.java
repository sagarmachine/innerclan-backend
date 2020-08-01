package com.innerclan.v1.exception;

import lombok.Getter;

@Getter
public class EmailNotSentToAllSubscription extends RuntimeException {

    String message;

    public EmailNotSentToAllSubscription(String message){
        this.message=message;
    }

}
