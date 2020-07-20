package com.innerclan.v1.controller.admin;

import com.innerclan.v1.entity.Promo;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IPromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping(value="/api/v1/admin/promo")
public class AdminPromoController {

    @Autowired
    IPromoService promoService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @PostMapping(value="/")
    public ResponseEntity<?> addPromo(@Valid @RequestBody Promo promo, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            bindingErrorService.getErrorResponse(bindingResult);

        promoService.addPromo(promo);
        
        
        return new ResponseEntity<>("Promo Successfully Added", HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/")
    public ResponseEntity<?> getPromos(){

        return new ResponseEntity<>(promoService.getPromosOrderByDate(), HttpStatus.OK);

    }

    @GetMapping(value="/getPublicPromos")
    public ResponseEntity<?> getPublicPromos(){

        return new ResponseEntity<>(promoService.getPublicPromos(), HttpStatus.OK);

    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deletePromo(@PathVariable("id") long id){

        promoService.deletePromo(id);
        return new ResponseEntity<>("Promo Successfully Deleted", HttpStatus.OK);
    }


    @GetMapping(value="/isValid/{promo}")
    public ResponseEntity<?> isPromoValid(Principal principal,@PathVariable("promo") String promo){
        String email=principal.getName();
        //String email="nikhilkhari0047@gmail.com";
       HashMap<Double,String> result = promoService.isPromoValid(promo,email);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }




}
