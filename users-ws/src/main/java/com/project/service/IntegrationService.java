package com.project.service;

import feign.Response;

import java.util.Map;

public interface IntegrationService {
    Response getRandomSuggestions(Map<String, String> headerMap);
}
