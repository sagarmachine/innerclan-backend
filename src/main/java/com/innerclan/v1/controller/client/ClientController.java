package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    IClientService clientService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @PostMapping("")
    public ResponseEntity<?> addClient(@Valid @RequestBody AddClientDto clientDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingErrorService.getErrorResponse(bindingResult);
        }
        clientService.addClient(clientDto);
        return new ResponseEntity<>("CLIENT ADDED SUCCESSFULLY", HttpStatus.OK);
    }


}
