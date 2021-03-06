package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.AddToCartDto;
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
@RequestMapping(value="/api/v1/client/cart")
//@CrossOrigin(value = "/**/*",origins = "/**/*", allowedHeaders = "Authorization",exposedHeaders = "Authorization")
public class ClientCartController {

    @Autowired
    ICartItemService cartItemService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @Autowired
    IPromoService promoService;

    @Autowired
    ClientRepository clientRepository;


    @PostMapping(value="")
    public ResponseEntity<?> addCartItem(Principal principal, @RequestBody AddToCartDto addToCartDto){
        String email=principal.getName();
      cartItemService.addCartItem(email,addToCartDto);
        return new ResponseEntity<>("ITEMs ADDED SUCCESSFULLY AND UPDATED", HttpStatus.OK);
    }

    @PostMapping(value="/test")
    public ResponseEntity<?> addCartItemTest( @RequestBody AddToCartDto addToCartDto){
        String email="nikhilkhari47@gmail.com";
        cartItemService.addCartItem(email,addToCartDto);
        return new ResponseEntity<>("ITEMs ADDED SUCCESSFULLY AND UPDATED", HttpStatus.OK);
    }

    @GetMapping(value="")
    public ResponseEntity<?> getCartItems(Principal principal ){

        String email=principal.getName();
        List<CartItemDto> result=cartItemService.getCartItems(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value="/test")
    public ResponseEntity<?> getCartItems2( ){

        String email="nikhilkhari47@gmail.com";
        List<CartItemDto> result=cartItemService.getCartItems(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping(value="")
    public ResponseEntity<?> deleteCartItem(Principal principal,@RequestParam("colorId") long colorId,@RequestParam("size") String size){

        String email=principal.getName();
        cartItemService.deleteCartItem(email,colorId,size);
        return new ResponseEntity<>("ITEM DELETED SUCCESSFULLY TO THE CART", HttpStatus.OK);
    }







    //only for testing purpose don't delete

    @GetMapping(value="/test2")
    public ResponseEntity<?> getCartItems3( ){
        String email="nikhilkhari47@gmail.com";


        Optional<Client> clientOptional=clientRepository.findByEmail(email);
        if(!clientOptional.isPresent()) throw new ClientNotFoundException("Client not found with id : "+email);
        CheckOutDto checkOutDto =new CheckOutDto();
        AddToCartDto cartItem1=new AddToCartDto(1,2,"SMALL");
        AddToCartDto cartItem2=new AddToCartDto(2,4,"MEDIUM");
        ArrayList list=new ArrayList();
        list.add(cartItem1); list.add(cartItem2);
        checkOutDto.setAddToCartDtoList(list);



        HashMap<String,String> promoValid= promoService.isPromoValid("WELCOME250",email);
        log.info("1------------"+promoValid.get("Valid Promo Code"));
        double promoValue=Double.parseDouble(promoValid.get("value"));
        //if(promoValue==-1||promoValue==0) throw new PromoNotFoundException(promoValid.get("message"));
        log.info("2------------");
        cartItemService.deleteAllCartItems(email);
        log.info("3------------");
        cartItemService.addCartItems(email,checkOutDto.getAddToCartDtoList());
        double cartTotal= cartItemService.getCartTotal(email);

        if(promoValue==-1) promoValue=0;
        double netTotal=cartTotal-promoValue;
        String[] result=new String[]{cartTotal+"",netTotal+""};
        return new ResponseEntity<>(result, HttpStatus.OK);
    }








}
