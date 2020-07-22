package com.innerclan.v1.service;

import java.util.TreeMap;

public interface IPaytmService {

     boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception ;
         String getCheckSum(TreeMap<String, String> parameters) throws Exception ;

    }
