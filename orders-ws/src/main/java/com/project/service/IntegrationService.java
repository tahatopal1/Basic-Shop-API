package com.project.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IntegrationService {
    ResponseEntity validateAuthenticationToken(Map<String, String> headerMap);
}
