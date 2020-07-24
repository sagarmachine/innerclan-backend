package com.innerclan.v1.service;

public interface IEmailService {

    public void sendSimpleMessage(String to,String link, String subject, String body);

    void changePassword(String email);
}
