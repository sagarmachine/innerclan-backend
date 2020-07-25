package com.innerclan.v1.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.TreeMap;

public interface IPaytmService {

     boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception ;
     String getCheckSum(TreeMap<String, String> parameters) throws Exception ;

     void placeOrder(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> checkOut(String email, double totalprice);

    // ResponseEntity<?> makePayment(String orderId, Double amount, String clientUUID);

    }
