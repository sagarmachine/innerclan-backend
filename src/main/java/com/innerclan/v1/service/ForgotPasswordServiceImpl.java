package com.innerclan.v1.service;


import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.ForgotPassword;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImpl implements IForgotPasswordService {

    @Autowired
    ClientRepository clientRepository;


    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    IEmailService emailService;

    @Override
    public void changePassword(String email) {

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if(!clientOptional.isPresent()) throw new ClientNotFoundException("EMAIL ID NOT REGISTERED ");

        String uuid= UUID.randomUUID().toString();
        ForgotPassword forgotPassword =new ForgotPassword(email,uuid);

        forgotPasswordRepository.save(forgotPassword);
        String subject="PASSWORD RESET LINK";
        String body="CLICK ON THE LINK TO RESET YOUR PASSWORD";
        emailService.sendPasswordResetMessage(email,uuid,"INNERCLAN password rest");

    }
}
