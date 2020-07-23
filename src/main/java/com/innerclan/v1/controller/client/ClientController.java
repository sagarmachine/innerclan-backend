package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.LoginDto;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    IClientService clientService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddClientDto clientDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingErrorService.getErrorResponse(bindingResult);
        }

        return clientService.addClient(clientDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){
            bindingErrorService.getErrorResponse(bindingResult);
        }

        return clientService.authenticateClient(loginDto);
    }

}
