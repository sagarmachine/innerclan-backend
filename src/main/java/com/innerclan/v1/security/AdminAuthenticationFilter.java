package com.innerclan.v1.security;

import com.innerclan.v1.repository.AdminLoginKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
@Slf4j
public class AdminAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AdminLoginKeyRepository adminLoginKeyRepository;
    @Autowired
    JWTUtil jwtUtil;


    @Value(value = "${innerclan.admin.id}")
    String id;
    @Value(value = "${innerclan.admin.secret}")
    String secret;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorization= httpServletRequest.getHeader("Authorization");
        String key= httpServletRequest.getHeader("Key");
        if(key==null || authorization==null)
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        if(!adminLoginKeyRepository.findByKey(key).isPresent())
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        if(authorization!=null && authorization.length()>7){
            String jwtToken = authorization.substring(7);
            String email = jwtUtil.getUsernameFromToken(jwtToken);
           // UserDetails user =clientService.loadUserByUsername(email);
            User user = new User(id, secret, new ArrayList<>());

            if(jwtUtil.validateToken(jwtToken,user) && SecurityContextHolder.getContext().getAuthentication()==null){
                Collection authorities= new ArrayList();
                 authorities.add("ADMIN");
           UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),null,authorities );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                log.info("Admin Authentication SUCCESS "+ SecurityContextHolder.getContext().getAuthentication().isAuthenticated() );
            }
        }



    }
}
