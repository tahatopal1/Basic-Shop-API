package com.project.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("products-ws")
public interface ProductClient {

    @GetMapping("/v1/products/product")
    Response getProduct(@RequestParam String code);

    @PutMapping("/v1/products/exchange")
    ResponseEntity exchange(@RequestParam String code, @RequestParam Integer count);

}
