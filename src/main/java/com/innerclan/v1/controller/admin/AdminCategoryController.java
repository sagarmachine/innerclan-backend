package com.innerclan.v1.controller.admin;


import com.innerclan.v1.dto.AddCategoryDto;
import com.innerclan.v1.dto.AddProductDto;
import com.innerclan.v1.entity.Category;
import com.innerclan.v1.exception.CategoryNotFoundException;
import com.innerclan.v1.repository.CategoryRepository;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/admin/category")
@CrossOrigin(value = {"http://localhost:3001","http://localhost:3000"})
public class AdminCategoryController {


    @Autowired
    IBindingErrorService bindingErrorService;


    @Autowired
    ICategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


    @PostMapping(value="")
    public ResponseEntity<?> addCategory(@Valid @RequestBody AddCategoryDto category, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return bindingErrorService.getErrorResponse(bindingResult);
        }

        categoryService.addCategory(category);
        return new ResponseEntity<>(category,HttpStatus.ACCEPTED);


    }
    @PutMapping(value="")
    public ResponseEntity<?> updateCategory(@RequestBody AddCategoryDto category) {

         categoryService.updateCategory(category);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {

        categoryService.deleteCategory(id);

        return new ResponseEntity<>("SUCCESSFULLY Deleted",HttpStatus.OK);
    }


    @GetMapping(value = "")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> getcategory(@PathVariable("id") long id){

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(!categoryOptional.isPresent())
            throw  new CategoryNotFoundException("no category with id "+id+" found");

        return ResponseEntity.ok().body(categoryOptional.get());
    }

}
