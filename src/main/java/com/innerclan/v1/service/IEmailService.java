package com.innerclan.v1.service;

public interface IEmailService {

    public void sendSimpleMessage(String to,String link, String subject, String body);

    public void sendPasswordResetMessage(String to, String uuid, String subject);

}
