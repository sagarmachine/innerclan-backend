package com.innerclan.v1.service;

import com.innerclan.v1.entity.Subscription;
import com.innerclan.v1.exception.SubsciberAlreadyExistException;
import com.innerclan.v1.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepo;


    @Override
    public void addSubscriber(String email) {

        Subscription subscription=new Subscription();
        subscription.setEmail(email);
       // subscription.setCreatedOn(new java.util.Date());
        try{
        subscriptionRepo.save(subscription);
    }
        catch(Exception ex) {
            throw new SubsciberAlreadyExistException("Subsciber with email " + email + " has already subscibed");

        }
        }
}
