package com.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UsersWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersWsApplication.class, args);
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
