package com.innerclan.v1.security;

import com.innerclan.v1.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IClientService clientService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("Authenticating");

        String authorization= httpServletRequest.getHeader("Authorization");

        if(authorization!=null && authorization.length()>7){
            String jwtToken = authorization.substring(7);
            String email = jwtUtil.getUsernameFromToken(jwtToken);
            UserDetails user =clientService.loadUserByUsername(email);
            if(jwtUtil.validateToken(jwtToken,user) && SecurityContextHolder.getContext().getAuthentication()==null){

                Object userDetails;
                Object principal;
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
             SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
            }
        }

filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
