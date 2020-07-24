package com.innerclan.v1.service;

//import com.personalprojecttracker.demo.model.Image;
//import com.personalprojecttracker.demo.repository.ImageRepo;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.ForgotPassword;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender emailSender;


    @Value(value = "${innerclan.admin.email}")
    String adminEmail;

    @Value(value = "${innerclan.frontend.client.passwordResetUrl}")
    String passwordResetUrl;



    @Override
    public void sendSimpleMessage(String to,String link, String subject, String body) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

   String inlineImage = "<img src='"+link+"' alt=\"anuj\" border=\"0\"></body>\n<br/>"+body;
       helper.setText(inlineImage, true);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setFrom(adminEmail);
        emailSender.send(message);
        helper.setTo(to);
       emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPasswordResetMessage(String to, String uuid, String subject) {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            String inlineImage = "Click  below to reset your password <br> <a href='"+passwordResetUrl+"/"+uuid+"'>CLICK</a>";
            helper.setText(inlineImage, true);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setFrom(adminEmail);
            emailSender.send(message);
            helper.setTo(to);
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
