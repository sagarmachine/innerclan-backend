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

@RestController
@RequestMapping(value="/api/v1/admin/promo")
public class AdminPromoController {

    @Autowired
    IPromoService promoService;

    @Autowired
    IBindingErrorService bindingErrorService;
    @PostMapping(value="/")
    public ResponseEntity<?> addPromo(@Valid @RequestBody Promo promo, BindingResult bindingResult){
        if (bindingResult.hasErrors());

        
        
        return new ResponseEntity<>("hgb", HttpStatus.ACCEPTED); 
    }

}
