package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/admin/category")
public class AdminCategoryController {


    @Autowired
    IBindingErrorService bindingErrorService;


    @Autowired
    ICategoryService categoryService;


    @PostMapping(value="/")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return bindingErrorService.getErrorResponse(bindingResult);
        }

        categoryService.addCategory(category);
        return new ResponseEntity<>(category,HttpStatus.ACCEPTED);


    }
    @PutMapping(value="/")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {

         categoryService.updateCategory(category);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {

        categoryService.deleteCategory(id);

        return new ResponseEntity<>("SUCCESSFULLY Deleted",HttpStatus.OK);
    }



}
