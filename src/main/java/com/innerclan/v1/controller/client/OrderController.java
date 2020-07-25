package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.CheckOutDto;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.PromoNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {


    @Autowired
    IPaytmService paytmService;

    @Autowired
    PaytmClient paytmClient;

    @Autowired
    ICartItemService cartItemService;

    @Autowired
    IPromoService promoService;

    @Autowired
    ClientRepository clientRepository;


    @GetMapping(value="")
    public ResponseEntity<?> orderCheckOut(Principal principal, @RequestBody CheckOutDto checkOutDto){
        String email=principal.getName();
        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");

        cartItemService.deleteAllCartItems(email);

        double cartTotal=cartItemService.addCartItems(email,checkOutDto.getCartItemList());
        double netTotal=0;

if(!checkOutDto.getPromo().equals("NULL")) {
    HashMap<String, Double> promoValid = promoService.isPromoValid(checkOutDto.getPromo(), email);
    if (!promoValid.containsKey("Valid Promo Code")) throw new PromoNotFoundException("INVALID PROMO CODE");
       netTotal = cartTotal - promoValid.get("Valid Promo Code");
}
else netTotal = cartTotal;
        return paytmService.checkOut(email,netTotal);


    }

//    @PostMapping(value = "/pgredirect")
//    public ResponseEntity<?>
//        // ModelAndView
//    getRedirect(@RequestParam(name = "customerID") String customerId,
//                @RequestParam(name = "amount") String transactionAmount,
//                @RequestParam(name = "orderID") String orderId) throws Exception {
//        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmClient.getPaytmUrl());
//
//        log.info("AMT--->"+transactionAmount);
//        TreeMap<String, String> parameters = new TreeMap<>();
//        paytmClient.getDetails().forEach((k, v) -> parameters.put(k, v));
//        // parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
//        //  parameters.put("EMAIL", env.getProperty("paytm.email"));
//        parameters.put("ORDER_ID", orderId);
//        parameters.put("TXN_AMOUNT", transactionAmount);
//        parameters.put("CUST_ID", customerId);
//        String checkSum = paytmService.getCheckSum(parameters);
//        parameters.put("CHECKSUMHASH", checkSum);
//
//        log.info("PAYTM CHECKSUM--------------------------> "+ parameters);
//        modelAndView.addAllObjects(parameters);
//        log.info("PAYTM CHECKSUM--------------------------> "+ modelAndView);
//        // return modelAndView;
//
//        return new ResponseEntity(parameters, HttpStatus.ACCEPTED);
//    }


    @PostMapping(value = "/pgresponse")
    public String getResponseRedirect(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.info("PAYTM RESPONSE-------------------------->");

        Map<String, String[]> mapData = request.getParameterMap();
        TreeMap<String, String> parameters = new TreeMap<String, String>();
        mapData.forEach((key, val) -> parameters.put(key, val[0]));
        String paytmChecksum = "";
        if (mapData.containsKey("CHECKSUMHASH")) {
            paytmChecksum = mapData.get("CHECKSUMHASH")[0];
        }
        String result;
        log.info("PAYTM CHECKSUm  --------------------------> "+paytmChecksum);

        boolean isValideChecksum = false;
        System.out.println("RESULT : "+parameters.toString());
        try {
            isValideChecksum = paytmService.validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
                if (parameters.get("RESPCODE").equals("01")) {
                    result = "S";
                    log.info("PAYTM SUCCESSFUL-------------------------->");

                } else {
                    result = "F";
                    log.info("PAYTM FAIL-------------------------->");

                }
            } else {
                log.info("PAYTM CHECKSUm MISsMATCH --------------------------> "+paytmChecksum);
                result = "Checksum mismatched";
            }
        } catch (Exception e) {
            log.info("PAYTM EXC-------------------------->");

            result = e.toString();
        }
        model.addAttribute("result",result);
        parameters.remove("CHECKSUMHASH");
        model.addAttribute("parameters",parameters);

        try {
            if(result.equals("S"))
                response.sendRedirect("http://localhost:3000/paytmS");
            else
                response.sendRedirect("http://localhost:3000/paytmF");

        } catch (IOException e) {
            e.printStackTrace();
            log.info("PAYTM EXC 22-------------------------->");

        }


        return "report";
    }


}
