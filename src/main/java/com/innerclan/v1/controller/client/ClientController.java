package com.innerclan.v1.controller.client;

import com.innerclan.v1.dto.AddClientDto;
import com.innerclan.v1.dto.LoginDto;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.security.JWTUtil;
import com.innerclan.v1.service.IBindingErrorService;
import com.innerclan.v1.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value="/api/v1/client")
public class ClientController {

    @Autowired
    IClientService clientService;

    @Autowired
    IBindingErrorService bindingErrorService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Value(value = "${innerclan.frontend.client.oauthRedirectUrl}")
    String oauthRedirectUrl;




    @PostMapping(value="/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddClientDto clientDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingErrorService.getErrorResponse(bindingResult);
        }

        return clientService.addClient(clientDto);
    }


    @PostMapping(value="/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){
            bindingErrorService.getErrorResponse(bindingResult);
        }

        return clientService.authenticateClient(loginDto);
    }

}
