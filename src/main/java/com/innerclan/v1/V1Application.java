package com.innerclan.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class V1Application {

    public static void main(String[] args) {
        SpringApplication.run(V1Application.class, args);
    }

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver
//                = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(5242880);
//        return multipartResolver;
//    }

}
