package com.innerclan.v1.service;


import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.OrderRepository;
import com.innerclan.v1.security.JWTUtil;
import com.paytm.pg.merchant.CheckSumServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

@Service
@Slf4j
public class PaytmServiceImpl implements IPaytmService {

    @Autowired
    PaytmClient paytmClient;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    IOrderService orderService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    OrderRepository orderRepository;

    public  boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
        return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(paytmClient.getMerchantKey(),
                parameters, paytmChecksum);
    }


    public  String getCheckSum(TreeMap<String, String> parameters) throws Exception {
        return CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(paytmClient.getMerchantKey(), parameters);
    }

    @Override
    public  ResponseEntity<?>  checkOut(String email, double amount,double promoDiscount,String promoUsed ,Address address) {
   Optional<Client> clientOptional= clientRepository.findByEmail(email);
        if(!clientOptional.isPresent())
            throw  new ClientNotFoundException("no client found by id : "+ email );

        String orderId= createOrderId(clientOptional.get());
        orderService.createOrder(clientRepository.findByEmail(email).get(),amount,promoDiscount,promoUsed,address,clientRepository.findByEmail(email).get().getCartItems(),orderId);

      return  makePayment(orderId, amount, clientOptional.get().getUuid());

    }

    @Override
    public void placeOrder(HttpServletRequest request, HttpServletResponse response) {



        Map<String, String[]> mapData = request.getParameterMap();
        TreeMap<String, String> parameters = new TreeMap<String, String>();
        mapData.forEach((key, val) -> parameters.put(key, val[0]));

        String paytmChecksum = "";
        if (mapData.containsKey("CHECKSUMHASH")) {
            paytmChecksum = mapData.get("CHECKSUMHASH")[0];
        }
        String result;

        boolean isValideChecksum = false;

        try {
            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
                if (parameters.get("RESPCODE").equals("01")) {
                    result = "S";
                    log.info("PAYTM SUCCESSFUL--------------------------> ");
                    log.info(parameters.toString());
                    log.info("TXN_ID :"+parameters.get("TXNID"));
                    log.info("PAYMENTMODE :"+parameters.get("PAYMENTMODE"));
                    log.info("PAYMENTMODE :"+parameters.get("ORDERID"));
                    ;
                } else {
                    result = "F";
                    log.info("PAYTM FAILED-------------------------->");

                }
            } else {
                log.info("PAYTM CHECKSUm MISsMATCH --------------------------> "+paytmChecksum);
                result = "Checksum mismatched";
            }
        } catch (Exception e) {
            log.info("PAYTM EXC--------------------------> "+e.getMessage());

            result = e.toString();
        }

        Client client=orderRepository.findByOrderId(parameters.get("ORDERID")).get().getClient();

        UserDetails user =clientService.loadUserByUsername(client.getEmail());
        String jwtToken = jwtUtil.generateToken(user);

        try {
            if(result.equals("S")) {
                orderService.completeOrder(parameters.get("ORDERID"),parameters.get("TXNID"),parameters.get("PAYMENTMODE"));

                response.sendRedirect("http://sheltered-scrubland-77233.herokuapp.com/paymentResult/" + jwtToken + "/" + client.getEmail() + "/" + client.getFirstName());
            }else {
                orderService.orderFailed(parameters.get("ORDERID"));
                response.sendRedirect("http://sheltered-scrubland-77233.herokuapp.com/paymentResult/"+jwtToken+"/"+client.getEmail()+"/"+client.getFirstName());
            }
        } catch (IOException e) {
            orderService.orderFailed(parameters.get("ORDERID"));
            log.info("PAYTM EXC 22-------------------------->");

         }


        //return "report";
    }


    String createOrderId(Client client){
        return UUID.randomUUID().toString();
    }

//    double calculateAmount(Client client){
//        return 0;
//    }

    //@Override
    public  ResponseEntity<?>  makePayment(String orderId, Double amount, String clientUUID) {
        TreeMap<String, String> parameters = new TreeMap<>();
        paytmClient.getDetails().forEach((k, v) -> parameters.put(k, v));
        // parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
        //  parameters.put("EMAIL", env.getProperty("paytm.email"));
        parameters.put("ORDER_ID", orderId);
        parameters.put("TXN_AMOUNT", amount+"");
        parameters.put("CUST_ID", clientUUID);
        String checkSum = null;
        try {
            checkSum = getCheckSum(parameters);
        } catch (Exception e) {
            // Payment FailedException

        }
        parameters.put("CHECKSUMHASH", checkSum);

        return new ResponseEntity(parameters, HttpStatus.ACCEPTED);
    }


}
