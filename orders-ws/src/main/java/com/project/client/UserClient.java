package com.project.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient("users-ws")
public interface UserClient {

    @GetMapping("/validate")
    ResponseEntity validateAuthenticationToken(@RequestHeader Map<String, String> headerMap);

}
