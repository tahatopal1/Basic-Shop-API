package com.example.productws.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

public interface IntegrationService {
    ResponseEntity validateAuthenticationToken(Map<String, String> headerMap);
}
