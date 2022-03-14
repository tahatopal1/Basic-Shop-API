package com.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrdersWsApplication {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrdersWsApplication.class, args);
    }

}
