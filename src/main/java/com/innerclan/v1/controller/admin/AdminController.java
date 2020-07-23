package com.innerclan.v1.controller.admin;


import com.innerclan.v1.exception.AuthenticationException;
import com.innerclan.v1.security.JWTUtilAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    @Value(value = "${innerclan.admin.id}")
      String id;
    @Value(value = "${innerclan.admin.secret}")
    String secret;

    @Autowired
    JWTUtilAdmin jwtUtilAdmin;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAdmin(@RequestParam("id")String id, @RequestParam("secret")String secret) {

        if(this.id.equals(id) && this.secret.equals(secret)){
            Collection authorities= new ArrayList();
            //List<SimpleGrantedAuthority> grantedAuthorities = new AdminAuthority().getAuthorities().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList()); // (1)

            authorities.add("ADMIN");

            User user = new User("ADMIN", null, authorities);
            //String jwt = jwtUtilAdmin
            return null;
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
