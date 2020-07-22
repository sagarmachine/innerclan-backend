package com.innerclan.v1.security;

import com.innerclan.v1.entity.Client;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    IClientService clientService;

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
                   Client client= new Client();
                   client.setFirstName(map.get("given_name").toString());
                   client.setLastName(map.get("family_name").toString());
                   client.setEmail(map.get("email").toString());
                   clientRepository.save(client);

               }
        UserDetails userDetails = clientService.loadUserByUsername(email);
        String jwtToken = jwtUtil.generateToken(userDetails);
        httpServletResponse.addHeader("Authorization","Bearer "+jwtToken);
        httpServletResponse.addHeader("firstName", map.get("given_name").toString().toUpperCase());

        //String jwt
        httpServletResponse.sendRedirect("http://localhost:3000");

    }
}
