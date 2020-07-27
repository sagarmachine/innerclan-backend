package com.innerclan.v1.controller.admin;

import com.innerclan.v1.entity.UIElements;
import com.innerclan.v1.repository.UIElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin/ui")
public class AdminUIController {


    @Autowired
    UIElementsRepository uiElementsRepository;

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody UIElements uiElements){
        uiElementsRepository.save(uiElements);
        return new ResponseEntity<>("UI added successfully ", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>(uiElementsRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UIElements uiElements){
        uiElementsRepository.deleteAll();
        uiElementsRepository.save(uiElements);
        return new ResponseEntity<>("UI updated successfully", HttpStatus.OK);
    }
}
