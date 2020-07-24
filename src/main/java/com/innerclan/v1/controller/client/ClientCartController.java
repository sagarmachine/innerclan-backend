package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.ICartItemService;
import com.innerclan.v1.service.IClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/client/cart")
public class ClientCartController {

    @Autowired
    ICartItemService cartItemService;

    @Autowired
    IBindingErrorService bindingErrorService;


    @PostMapping(value="/{productId}")
    public ResponseEntity<?> addCartItem(Principal principal, @PathVariable("productId") List<CartItem> cartItemList){
        String email=principal.getName();
      cartItemService.addCartItems(email,cartItemList);
        return new ResponseEntity<>("ITEMs ADDED SUCCESSFULLY AND UPDATED", HttpStatus.OK);
    }

    @GetMapping("")
     public ResponseEntity<?> getCartItems(Principal principal ){

      String email=principal.getName();
   List<CartItemDto> result=cartItemService.getCartItems(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value="")
    public ResponseEntity<?> addCartItems(Principal principal, @ResponseBody productId){
        String email=principal.getName();
        cartItemService.addCartItem(email,productId);
        return new ResponseEntity<>("ITEM ADDED SUCCESSFULLY TO THE CART", HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteCartItem( @PathVariable("productId") long productId){
// Principal principal
// String email=principal.getName();
        String email= "sagarpanwar2122@gmail.com";
        cartItemService.deleteCartItem(email,productId);
        return new ResponseEntity<>("ITEM DELETED SUCCESSFULLY TO THE CART", HttpStatus.OK);
    }




}
