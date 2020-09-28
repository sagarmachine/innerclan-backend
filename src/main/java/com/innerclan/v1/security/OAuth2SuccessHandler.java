package com.innerclan.v1.security;

import com.innerclan.v1.controller.client.ClientController;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    IClientService clientService;

    @Value(value = "${innerclan.frontend.client.oauthRedirectUrl}")
    String oauthRedirectUrl;

    @Autowired
    ClientController clientController;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;

        log.info("OAUTH successfull ------>");
        log.info(token.getPrincipal().getAttributes().get("given_name").toString());
        log.info(token.getPrincipal().getAttributes().get("family_name").toString());
        log.info(token.getPrincipal().getAttributes().get("email").toString());


        Map<String,Object> map=token.getPrincipal().getAttributes();
       String email= token.getPrincipal().getAttributes().get("email").toString();
               if(clientRepository.findByEmail(email).isPresent()){
                       Client client= clientRepository.findByEmail(email).get();
                       client.setLastLoggedIn(new Date());
                       client.setVisit(client.getVisit()+1);
                       clientRepository.save(client);

               }
               else{
                   BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
                   Client client= new Client();
                   client.setFirstName(map.get("given_name").toString());
                   client.setLastName(map.get("family_name").toString());
                   client.setEmail(map.get("email").toString());
                   client.setUuid(UUID.randomUUID().toString());
                   client.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
                   clientRepository.save(client);

               }
        UserDetails userDetails = clientService.loadUserByUsername(email);
        String jwtToken = jwtUtil.generateToken(userDetails);
       httpServletResponse.addHeader("Authorization","Bearer "+jwtToken);
       httpServletResponse.addHeader("firstName", map.get("given_name").toString().toUpperCase());

//       redirect(clientController.oauthAuthorizationLogin(authentication,map.get("email").toString()));
        //String jwt
        httpServletResponse.sendRedirect("http://localhost:3001/auth/"+jwtToken+"/"+map.get("email").toString()+"/"+map.get("given_name").toString());

    }

//    @ResponseBody
//    ModelAndView redirect(ModelAndView m){
//        return m;
//    }

}
