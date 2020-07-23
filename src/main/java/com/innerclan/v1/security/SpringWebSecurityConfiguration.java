package com.innerclan.v1.security;

import com.innerclan.v1.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@Slf4j
public class SpringWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserNotLoginAuthenticationEntryPoint userNotLoginAuthenticationEntryPoint;

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Autowired
    OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    OAuth2FailureHandler oAuth2FailureHandler;


    @Autowired
    IClientService clientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**/*").permitAll();
        http.exceptionHandling().authenticationEntryPoint(userNotLoginAuthenticationEntryPoint)
        .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .oauth2Login().successHandler(oAuth2SuccessHandler).failureHandler(oAuth2FailureHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clientService).passwordEncoder(getBCryptPasswordEnc());
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEnc(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(){
        try {
            return super.authenticationManagerBean();
        } catch (Exception e) {
          log.info("ERRROR On GETTING AUTHENTICATION MANAGER");
        }
        return null;
    }

}
