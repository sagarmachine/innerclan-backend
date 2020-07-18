package com.innerclan.v1.service;

//import com.personalprojecttracker.demo.model.Image;
//import com.personalprojecttracker.demo.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;


@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender emailSender;

//    @Autowired
//    ImageRepo imageRepo;

    @Value(value = "${admin.email.id}")
    String adminEmail;
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
}
