package com.innerclan.v1.controller.client;


import com.innerclan.v1.dto.ShowCase;
import com.innerclan.v1.repository.ShowcaseRepository;
import com.innerclan.v1.repository.UIElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client/ui")
public class ClientUIController {

    @Autowired
    ShowcaseRepository showcaseRepository;

    @Autowired
    UIElementsRepository uiElementsRepository;

    @GetMapping(value="")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>(uiElementsRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping(value = "/showcase")
    public ResponseEntity<?> getShowcase(){
        return new ResponseEntity<>(showcaseRepository.findAllByOrderByCreatedOnDesc(),HttpStatus.ACCEPTED);
    }
}
