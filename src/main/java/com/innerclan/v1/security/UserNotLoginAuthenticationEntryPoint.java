package com.innerclan.v1.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;




@Component
@Slf4j
public class UserNotLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("Login Exception");

        httpServletResponse.setHeader("Message","please login ");
        httpServletResponse.setStatus(7571);
        PrintWriter writer=httpServletResponse.getWriter();
         writer.print("please login with valid credentials  "+ SecurityContextHolder.getContext().getAuthentication());
    }
}
