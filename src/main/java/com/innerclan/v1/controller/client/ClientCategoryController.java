package com.innerclan.v1.controller.client;


import com.innerclan.v1.entity.Category;
import com.innerclan.v1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/client/category")
public class ClientCategoryController {
    @Autowired
    CategoryRepository categoryRepository;


    @GetMapping(value = "")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }
}
