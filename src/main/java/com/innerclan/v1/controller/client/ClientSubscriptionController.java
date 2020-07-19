package com.innerclan.v1.controller.client;

import com.innerclan.v1.service.ISubscriptionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping(value="/api/v1/subscribe")
public class ClientSubscriptionController {

    @Autowired
    ISubscriptionService subscriptionService;

    @PostMapping(value="/")
    public ResponseEntity<?> addSubscription(@RequestParam("email") String email){

        subscriptionService.addSubscriber(email);
        return new ResponseEntity<>("SUCESSFULLY SUBSCRIBED", HttpStatus.ACCEPTED);


    }
}
