package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.service.IBindingErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController("api/v1/admin/product")
public class AdminProductController {


    @Autowired
    IBindingErrorService bindingErrorService;

    @PostMapping(value = "/addProduct")
    ResponseEntity<?> addAProject(@Valid @RequestBody AddProductDto addProductDto, @RequestParam MultipartFile file, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
     return bindingErrorService.getErrorResponse(bindingResult);
        }
        //Service
      //  return new ResponseEntity<>("SUCC", HttpStatus.OK);
    }

}
