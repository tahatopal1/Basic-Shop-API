package com.project.configuration;

import com.project.security.GeneralPurposeInterceptor;
import com.project.service.IntegrationService;
import com.project.service.IntegrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
