package com.example.productws.configuration;

import com.example.productws.client.UserClient;
import com.example.productws.security.GeneralPurposeInterceptor;
import com.example.productws.service.IntegrationService;
import com.example.productws.service.IntegrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GeneralPurposeInterceptor(getIntegrationServiceBean()));
    }

    @Bean
    public IntegrationService getIntegrationServiceBean(){
        return new IntegrationServiceImpl();
    }


}
