package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.CartItemDto;
import com.innerclan.v1.dto.CheckOutDto;
import com.innerclan.v1.entity.CartItem;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.PromoNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.ICartItemService;
import com.innerclan.v1.service.IClientService;
import com.innerclan.v1.service.IPromoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/v1/client/cart")
public class ClientCartController {

    @Autowired
    ICartItemService cartItemService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @Autowired
    IPromoService promoService;

    @Autowired
    ClientRepository clientRepository;


    @PostMapping(value="/{productId}")
    public ResponseEntity<?> addCartItem( Principal principal, @PathVariable("productId") long productId){
        String email=principal.getName();
      cartItemService.addCartItem(email,productId);
        return new ResponseEntity<>("ITEMs ADDED SUCCESSFULLY AND UPDATED", HttpStatus.OK);
    }

//    @PostMapping(value="/{productId}")
//    public ResponseEntity<?> addCartItem( @PathVariable("productId") long productId){
//        String email="sagarpanwar2122@gmail.com";
//        cartItemService.addCartItem(email,productId);
//        return new ResponseEntity<>("ITEMs ADDED SUCCESSFULLY AND UPDATED", HttpStatus.OK);
//    }


    @GetMapping("")
     public ResponseEntity<?> getCartItems(Principal principal ){

      String email=principal.getName();
   List<CartItemDto> result=cartItemService.getCartItems(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
 //only for testing purpose don't delete

    @GetMapping("/test")
    public ResponseEntity<?> getCartItems2( ){
        String email="sagarpanwar2122@gmail.com";


        Optional<Client> clientOptional=clientRepository.findByEmail(email);
        if(!clientOptional.isPresent()) throw new ClientNotFoundException("Client not found with id : "+email);
        CheckOutDto checkOutDto =new CheckOutDto();
        CartItem cartItem1=new CartItem(clientOptional.get(),1,1);
        CartItem cartItem2=new CartItem(clientOptional.get(),2,1);
        ArrayList list=new ArrayList();
        list.add(cartItem1); list.add(cartItem2);
        checkOutDto.setCartItemList(list);
        checkOutDto.setPromo("WELCOME250");


        HashMap<String,Double> promoValid= promoService.isPromoValid(checkOutDto.getPromo(),email);
        log.info("1------------"+promoValid.get("Valid Promo Code"));
        if(!promoValid.containsKey("Valid Promo Code")) throw new PromoNotFoundException("INVALID PROMO CODE");
        log.info("2------------");
        cartItemService.deleteAllCartItems(email);
        log.info("3------------");
        double cartTotal=cartItemService.addCartItems(email,checkOutDto.getCartItemList());

        double netTotal=cartTotal-promoValid.get("Valid Promo Code");
        String[] result=new String[]{cartTotal+"",netTotal+""};
        return new ResponseEntity<>(result, HttpStatus.OK);
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
