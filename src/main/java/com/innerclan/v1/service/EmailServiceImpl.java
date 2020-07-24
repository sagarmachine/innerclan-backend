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

//    @Autowired
//    ImageRepo imageRepo;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;

    @Value(value = "nikhilkhari0047@gmail.com")
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

    @Override
    public void changePassword(String email) {

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if(!clientOptional.isPresent()) throw new ClientNotFoundException("EMAIL ID NOT REGISTERED ");

        UUID uuid= UUID.randomUUID();
        ForgotPassword forgotPassword =new ForgotPassword(email,uuid.toString());

        forgotPasswordRepository.save(forgotPassword);
        String link="https://www.google.com/url?sa=i&url=https%3A%2F%2Festv.in%2Fpsbindia%2Freset-password.php&psig=AOvVaw1IAwEmj0fXN25MfoKCtILY&ust=1595651313253000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKDF3sWG5eoCFQAAAAAdAAAAABAD";
        String subject="PASSWORD RESET LINK";
        String body="CLICK ON THE LINK TO RESET YOUR PASSWORD";
        sendSimpleMessage(email,link, subject, body);


    }
}
