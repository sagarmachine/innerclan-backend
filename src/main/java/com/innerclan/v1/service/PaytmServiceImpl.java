package com.innerclan.v1.service;


import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.OrderRepository;
import com.innerclan.v1.security.JWTUtil;
import com.paytm.pg.merchant.PaytmChecksum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    public  boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum){

        try{
            String actualAmount = (orderRepository.findByOrderId(parameters.get("ORDER_ID")).get().getTotal()) + "";

            System.out.println(actualAmount+"  "+parameters.get("TXN_AMOUNT"));

            if (parameters.get("TXN_AMOUNT").equals((actualAmount.substring(0, actualAmount.indexOf('.')))))
                return true;
            else
                return false;
        }catch (Exception ex){
            System.out.println("SOME EXC "+ex );
            return false;
        }

      //  return PaytmChecksum.verifySignature(parameters,paytmClient.getMerchantKey(),paytmChecksum);

//                CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(paytmClient.getMerchantKey(),
//                parameters, paytmChecksum);
    }


    public  String getCheckSum(TreeMap<String, String> parameters) throws Exception {
        return PaytmChecksum.generateSignature(parameters,paytmClient.getMerchantKey());
              //  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(paytmClient.getMerchantKey(), parameters);
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

//    private  boolean isValidKey(String key){
//
//        switch (key){
//
//            case "ORDER_ID":
//            case "ORDER_ID":
//            case "ORDER_ID":
//            case "ORDER_ID":
//            case "ORDER_ID":
//            case "ORDER_ID":
//            case "TXN_AMOUNT":
//            case  "CUST_ID": return true;
//
//        }
//
//    }

    @Override
    public void placeOrder(HttpServletRequest request, HttpServletResponse response, String CUST_ID) {

        Map<String, String[]> mapData = request.getParameterMap();
        System.out.println(mapData);
        TreeMap<String, String> parameters = new TreeMap<String, String>();
//        mapData.forEach((key, val) -> {
//        //    if () {
//                parameters.put(key, val[0]);
//       //     }
//        });

            paytmClient.getDetails().forEach((k, v) -> parameters.put(k, v));
            // parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
            //  parameters.put("EMAIL", env.getProperty("paytm.email"));
            parameters.put("ORDER_ID", mapData.get("ORDERID")[0]);
            parameters.put("TXN_AMOUNT", mapData.get("TXNAMOUNT")[0].substring(0,mapData.get("TXNAMOUNT")[0].indexOf('.')));
            parameters.put("CUST_ID", CUST_ID);
            parameters.put("CALLBACK_URL",parameters.get("CALLBACK_URL")+"?CUST_ID="+CUST_ID);



        System.out.println(parameters);
        System.out.println(CUST_ID);

        String paytmChecksum = "";
        if (mapData.containsKey("CHECKSUMHASH")) {
            paytmChecksum = mapData.get("CHECKSUMHASH")[0];
        }
        String result;

        boolean isValideChecksum = false;

        try {
            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && mapData.containsKey("RESPCODE")) {
                if (mapData.get("RESPCODE")[0].equals("01")) {
                    result = "S";

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

        Client client=orderRepository.findByOrderId(parameters.get("ORDER_ID")).get().getClient();

        UserDetails user =clientService.loadUserByUsername(client.getEmail());
        String jwtToken = jwtUtil.generateToken(user);

        try {
            if(result.equals("S")) {
                orderService.completeOrder(parameters.get("ORDER_ID"),parameters.get("TXN_ID"),parameters.get("PAYMENTMODE"));

                response.sendRedirect("http://localhost:3001/paymentResult/" + jwtToken + "/" + client.getEmail() + "/" + client.getFirstName());
            }else {
                orderService.orderFailed(parameters.get("ORDER_ID"));
                response.sendRedirect("http://localhost:3001/paymentResult/"+jwtToken+"/"+client.getEmail()+"/"+client.getFirstName());
            }
        } catch (IOException e) {
            orderService.orderFailed(parameters.get("ORDER_ID"));
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


     //   paytmClient.setCallbackUrl(paytmClient.getCallbackUrl()+"?CUST_ID="+clientUUID);

        paytmClient.getDetails().forEach((k, v) -> parameters.put(k, v));
        // parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
        //  parameters.put("EMAIL", env.getProperty("paytm.email"));
        parameters.put("ORDER_ID", orderId);
        parameters.put("TXN_AMOUNT",( amount+"").substring(0,(amount+"").indexOf('.')));
        parameters.put("CUST_ID", clientUUID);
        parameters.put("CALLBACK_URL",parameters.get("CALLBACK_URL")+"?CUST_ID="+clientUUID);

        System.out.println(parameters);
        String checkSum = null;
        try {
            checkSum = getCheckSum(parameters);
        } catch (Exception e) {
            // Payment FailedException

        }
        System.out.println(checkSum);
        parameters.put("CHECKSUMHASH", checkSum);

        return new ResponseEntity(parameters, HttpStatus.ACCEPTED);
    }


}
