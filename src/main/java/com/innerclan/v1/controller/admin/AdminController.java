package com.innerclan.v1.controller.admin;


import com.innerclan.v1.entity.Admin;
import com.innerclan.v1.entity.AdminLoginKey;
import com.innerclan.v1.exception.AuthenticationException;
import com.innerclan.v1.repository.AdminLoginKeyRepository;
import com.innerclan.v1.repository.AdminRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value="/api/v1/admin")
public class AdminController {


    @Autowired
    AdminLoginKeyRepository adminLoginKeyRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AdminRepository adminRepository;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value="/authenticate")
    public ResponseEntity<?> authenticateAdmin(@RequestParam("username")String username, @RequestParam("secret")String secret) {


           Optional<Admin> adminOptional= adminRepository.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
           if(!adminOptional.isPresent() || !bCryptPasswordEncoder.matches(secret,adminOptional.get().getSecret()))
               throw  new AuthenticationException("invalid credentials");

            User user = new User(username, bCryptPasswordEncoder.encode(secret), new ArrayList<>());
            String jwtToken = jwtUtil.generateToken(user);


            Admin admin =adminOptional.get();
            admin.setKey(UUID.randomUUID().toString());

            adminRepository.save(admin);

         HttpHeaders headers= new HttpHeaders();
         headers.add("Authorization","Bearer "+jwtToken);
         headers.add("Key",admin.getKey());



        return new ResponseEntity<>(headers, HttpStatus.OK);


    }


    @PostMapping(value = "")
    public void addAdmin(@RequestParam("username") String  username, @RequestParam("secret")String secret){

        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();


        Admin admin= new Admin();
        admin.setUsername(username);
        admin.setSecret(bCryptPasswordEncoder.encode(secret));
        adminRepository.save(admin);

    }




}



//class AdminAuthority implements GrantedAuthority{
//
//    @Override
//    public String getAuthority() {
//        return "ADMIN";
//    }
//}
