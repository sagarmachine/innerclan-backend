package com.innerclan.v1.service;

public interface IForgotPasswordService {

    void changePassword(String email);


    void updatePassword(String email, String password, String uuid);
}
