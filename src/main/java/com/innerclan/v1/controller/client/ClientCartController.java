package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.ICartItemService;
import com.innerclan.v1.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/client/cart")
public class ClientCartController {

    @Autowired
    ICartItemService cartItemService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @PostMapping(value="/{productId}")
    public ResponseEntity<?> addCartItem( @PathVariable("productId") long productId){

           //String email=principal.getName();
        String email= "sagarpanwar2122@gmail.com";
         cartItemService.addCartItem(email,productId);
        return new ResponseEntity<>("CLIENT ADDED SUCCESSFULLY", HttpStatus.OK);
    }


}
