package com.project.service;

import com.project.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IntegrationServiceImpl implements IntegrationService {

    @Autowired
    private UserClient userClient;

    @Override
    public ResponseEntity validateAuthenticationToken(Map<String, String> headerMap) {
        return userClient.validateAuthenticationToken(headerMap);
    }
}
