package com.project.client;

import feign.Request;
import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@FeignClient("products-ws")
public interface ProductClient {

    @GetMapping("/v1/products/suggestion")
    @CircuitBreaker(name = "products-ws", fallbackMethod = "getProductsFallback")
    @Retry(name = "products-ws")
    Response getRandomSuggestions(@RequestHeader Map<String, String> headerMap);

    default Response getProductsFallback(Map<String, String> headerMap, Throwable exception){
        return Response.builder()
                .status(200)
                .reason(exception.getMessage())
                .body(this.buildBody(), StandardCharsets.UTF_8)
                .request(this.buildRequest())
                .build();
    }

    private String buildBody(){
        return new StringBuilder().append("[")
                .append("{\"name\":\"Ipad Air 64GB Gri\",\"price\":8.999}")
                .append(",")
                .append("{\"name\":\"Macbook Pro Silver\",\"price\":37.499}")
                .append(",")
                .append("{\"name\":\"iPhone 11 128 Gb Beyaz\",\"price\":11.499}")
                .append("]")
                .toString();
    }

    private Request buildRequest(){
        return Request.create(Request.HttpMethod.GET, "", Map.of(), new byte[1], StandardCharsets.UTF_8);

    }

}
