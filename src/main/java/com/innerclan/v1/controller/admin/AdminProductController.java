package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/admin/product")
public class AdminProductController {


    @Autowired
    IBindingErrorService bindingErrorService;


    @Autowired
    ICategoryService productService;



    @PostMapping(value = "/addProduct")
    public  ResponseEntity<?> addProduct(@Valid @RequestBody AddProductDto addProductDto, @RequestParam MultipartFile file, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
     return bindingErrorService.getErrorResponse(bindingResult);
        }
        //Service
      return new ResponseEntity<>("SUCCESSFULLY SAVED", HttpStatus.OK);
    }




}
