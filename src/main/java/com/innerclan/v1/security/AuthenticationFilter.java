package com.innerclan.v1.security;

import com.innerclan.v1.entity.Admin;
import com.innerclan.v1.exception.AuthenticationException;
import com.innerclan.v1.repository.AdminRepository;
import com.innerclan.v1.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IClientService clientService;

    @Autowired
    AdminRepository adminRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("Authenticating");

        String authorization= httpServletRequest.getHeader("Authorization");
        String key = httpServletRequest.getHeader("Key");


        log.info(authorization);


        if(key!=null && authorization!=null) {
            log.info("0");

            authenticateAdmin(authorization, key,httpServletRequest,httpServletResponse,filterChain);
        }
        if(authorization!=null && authorization.length()>7){
            log.info("1");

            String jwtToken = authorization.substring(7);
            String email = jwtUtil.getUsernameFromToken(jwtToken);
            UserDetails user =clientService.loadUserByUsername(email);
            if(user!=null && jwtUtil.validateToken(jwtToken,user) && SecurityContextHolder.getContext().getAuthentication()==null  ){
                log.info("2");

                Object userDetails;
                Object principal;
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),null,new ArrayList<>() );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
               SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                log.info("SUCCESS "+ SecurityContextHolder.getContext().getAuthentication().isAuthenticated() );
            }
        }

filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    void authenticateAdmin(String authorization,String key,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)throws ServletException, IOException{
        if(authorization!=null && authorization.length()>7){
            String jwtToken = authorization.substring(7);
            String username = jwtUtil.getUsernameFromToken(jwtToken);
            Optional<Admin> adminOptional= adminRepository.findByUsername(username);

            if(adminOptional.isPresent() && adminOptional.get().getKey().equals(key) ){

                UserDetails user =new User(username, "secret", new ArrayList<>());
                if(jwtUtil.validateToken(jwtToken,user) && SecurityContextHolder.getContext().getAuthentication()==null) {
                    Object userDetails;
                    Object principal;
                   // Collection authorities= new ArrayList();
                  //  authorities.add("ADMIN");

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ADMIN"));
                    log.info(authorities.toString());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),null,authorities);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.setContext(context);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    log.info("SUCCESS "+ SecurityContextHolder.getContext().getAuthentication().isAuthenticated() );
                }
            }


        }
        if(key==null)
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }



}
