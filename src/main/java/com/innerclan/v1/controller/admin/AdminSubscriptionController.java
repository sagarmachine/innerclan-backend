package com.innerclan.v1.controller.admin;


import com.innerclan.v1.entity.Subscription;
import com.innerclan.v1.repository.SubscriptionRepository;
import com.innerclan.v1.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/api/v1/admin/subscription")
public class AdminSubscriptionController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    IEmailService emailService;

    @GetMapping("/mail")
    public void mailSubscription(@RequestParam("link")String link,
                                  @RequestParam("subject")String subject,
                                 @RequestParam("body")String body) {

        List<Subscription> subscriptionList = subscriptionRepository.findAll();

        for (Subscription subscription : subscriptionList) {
            emailService.sendSimpleMessage(subscription.getEmail(), link, subject, body);
        }

    }

        @GetMapping("/")
        public ResponseEntity<List<Subscription>> getAllSubscription(){

        return new ResponseEntity<>(subscriptionRepository.findAll(), HttpStatus.OK);

        }









}
