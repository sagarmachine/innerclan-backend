package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController("api/v1/admin/product")
public class AdminProductController {


    @Autowired
    IBindingErrorService bindingErrorService;


    @Autowired
    IProductService productService;

    @PostMapping(value = "/addProduct")
    public  ResponseEntity<?> addProduct(@Valid @RequestBody AddProductDto addProductDto, @RequestParam MultipartFile file, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
     return bindingErrorService.getErrorResponse(bindingResult);
        }
        //Service
      return new ResponseEntity<>("SUCCESSFULLY SAVED", HttpStatus.OK);
    }

    @PostMapping(value="/")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return bindingErrorService.getErrorResponse(bindingResult);
        }

        productService.addCategory(category);
        return new ResponseEntity<>(category,HttpStatus.ACCEPTED);


    }
    @PutMapping(value="/")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category) {

         productService.updateCategory(id, category);

        return new ResponseEntity<>("SUCCESSFULLY UPDATED",HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {

        productService.deleteCategory(id);

        return new ResponseEntity<>("SUCCESSFULLY Deleted",HttpStatus.OK);
    }

    
}
