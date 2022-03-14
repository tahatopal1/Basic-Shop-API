package com.example.productws.service;

import com.example.productws.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class IntegrationServiceImpl implements IntegrationService{

    @Autowired
    private UserClient userClient;

    @Override
    public ResponseEntity validateAuthenticationToken(Map<String, String> headerMap) {
        return userClient.validateAuthenticationToken(headerMap);
    }
}
