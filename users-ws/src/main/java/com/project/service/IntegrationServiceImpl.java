package com.project.service;

import com.project.client.ProductClient;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IntegrationServiceImpl implements IntegrationService {

    @Autowired
    private ProductClient productClient;

    @Override
    public Response getRandomSuggestions(Map<String, String> headerMap) throws Exception{
        return productClient.getRandomSuggestions(headerMap);
    }
}
