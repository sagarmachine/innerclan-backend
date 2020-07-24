package com.innerclan.v1.service;


import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.ForgotPassword;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.IllegalForgotPasswordRequestException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void changePassword(String email) {

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if(!clientOptional.isPresent()) throw new ClientNotFoundException("EMAIL ID NOT REGISTERED ");

        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByEmail(email);
        if(!forgotPasswordOptional.isPresent()) {
            String uuid = UUID.randomUUID().toString();
            ForgotPassword forgotPassword = new ForgotPassword(email, uuid);

            forgotPasswordRepository.save(forgotPassword);
            String subject = "PASSWORD RESET LINK";
            emailService.sendPasswordResetMessage(email, uuid, "INNERCLAN PASSWORD RESET LINK");

        }

        else
        {
            emailService.sendPasswordResetMessage(email,forgotPasswordOptional.get().getUuid(), "INNERCLAN PASSWORD RESET LINK");

        }

    }

    @Override
    public void updatePassword(String email, String password, String uuid) {

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if(!clientOptional.isPresent()) throw new ClientNotFoundException("EMAIL ID NOT REGISTERED ");

        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByEmailAndUuid(email,uuid);
        if(!forgotPasswordOptional.isPresent()) throw new IllegalForgotPasswordRequestException("Password reset link expired, Reset again");

        Client client =clientOptional.get();
        client.setPassword(bCryptPasswordEncoder.encode(password));
        forgotPasswordRepository.deleteById(forgotPasswordOptional.get().getId());
        clientRepository.save(client);

    }
}
