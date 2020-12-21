package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddToCartDto;
import com.innerclan.v1.dto.CheckOutDto;
import com.innerclan.v1.entity.Address;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Order;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.PromoNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.OrderRepository;
import com.innerclan.v1.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(value="/api/v1/order")
@Slf4j
public class CllientOrderController {


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

    @Autowired
    IOrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @PostMapping(value="/checkOut")
    public ResponseEntity<?> orderCheckOut(Principal principal, @RequestBody List<AddToCartDto> checkOutDto){
        String email=principal.getName();
        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");

        cartItemService.deleteAllCartItems(email);

        cartItemService.addCartItems(email,checkOutDto);

        return new ResponseEntity<>("CART ITEMS ADDEDD AND UPDATEDD SUCCESSFULLY",HttpStatus.OK);

    }

    /*
    @GetMapping("/getAddress")
    public ResponseEntity<?> placeOrder(Principal  principal){
        String email=principal.getName();
        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");

        return new ResponseEntity<>(clientValue.get().getAddress(),HttpStatus.OK);

    }

     */

    @GetMapping(value="/getAddress")
    public ResponseEntity<?> placeOrder(Principal principal){
        String email=principal.getName();

        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");

        return new ResponseEntity<>(clientValue.get().getAddress(),HttpStatus.OK);

    }

    @PostMapping (value="/placeOrder")
    public ResponseEntity<?> placeOrder(Principal  principal, @RequestParam("promo") String promo, @RequestBody Address address){

log.info("PLACING ORDER");
        String email=principal.getName();
        double cartTotal=cartItemService.getCartTotal(email);


        Optional<Client> clientValue= clientRepository.findByEmail(email);
        if(!clientValue.isPresent()) throw new ClientNotFoundException("Client with email "+email +"does not exist");

        Client client= clientValue.get();
        client.setAddress(address);
         clientRepository.save(client);

        double netTotal=cartTotal;
        double promoDiscount=0;
        promo=promo.toUpperCase();
        if(!promo.equals("-1")) {
            HashMap<String,String> promoValid = promoService.isPromoValid(promo,email);
            String promovalue = promoValid.get("value");
            promoDiscount =Double.parseDouble(promovalue);
            if (promoDiscount==-1) promoDiscount=0;
           // if (promoDiscount==-1) throw new PromoNotFoundException(promoValid.get("message"));
            netTotal = cartTotal-promoDiscount;

        }
        return paytmService.checkOut(email,netTotal,promoDiscount,promo,address);
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
    public String getResponseRedirect(@RequestParam("CUST_ID")String CUST_ID,HttpServletRequest request, HttpServletResponse response, Model model) {
    paytmService.placeOrder(request,response,CUST_ID);
      return "";
    }

    @GetMapping(value ="")
    public ResponseEntity<?> getAllOrders(Principal principal){

        Optional<Client> clientOptional=clientRepository.findByEmail(principal.getName());
        if(!clientOptional.isPresent())
            throw  new ClientNotFoundException("no client found with email"+ principal.getName());

        return new ResponseEntity<>(clientOptional.get().getOrders(),HttpStatus.OK);
    }

    @PostMapping(value="/addQuery/{id}")
    public ResponseEntity<?> addQuery(@PathVariable("id") long id, @RequestParam("query")  String query, Principal principal){

        return    orderService.addQuery(id,query,principal.getName());

    }


}
