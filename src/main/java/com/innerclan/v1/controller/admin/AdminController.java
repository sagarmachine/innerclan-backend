package com.innerclan.v1.controller.admin;


import com.innerclan.v1.entity.AdminLoginKey;
import com.innerclan.v1.exception.AuthenticationException;
import com.innerclan.v1.repository.AdminLoginKeyRepository;
import com.innerclan.v1.security.JWTUtil;
import com.innerclan.v1.security.JWTUtilAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    @Value(value = "${innerclan.admin.id}")
      String id;
    @Value(value = "${innerclan.admin.secret}")
    String secret;

    @Autowired
    AdminLoginKeyRepository adminLoginKeyRepository;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAdmin(@RequestParam("id")String id, @RequestParam("secret")String secret) {

        if(this.id.equals(id) && this.secret.equals(secret)){
            AdminLoginKey adminLoginKey = new AdminLoginKey();
            adminLoginKey.setKey(UUID.randomUUID().toString());
            adminLoginKeyRepository.save(adminLoginKey);

            User user = new User(id, secret, new ArrayList<>());
            String jwtToken = jwtUtil.generateToken(user);
            HttpHeaders headers= new HttpHeaders();
            headers.add("Authorization","Bearer "+jwtToken);
            headers.add("Key",adminLoginKey.getKey());

            return new ResponseEntity<>(headers, HttpStatus.OK);
        }else{
            throw new AuthenticationException("admin's credentials are invalid");
        }

    }



}



//class AdminAuthority implements GrantedAuthority{
//
//    @Override
//    public String getAuthority() {
//        return "ADMIN";
//    }
//}
