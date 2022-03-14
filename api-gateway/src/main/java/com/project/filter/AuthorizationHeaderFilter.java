package com.project.filter;

import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;


@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private Environment environment;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("Trying: " + request.getMethodValue() + "-" + request.getPath());
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            if (!isJwtValid(jwt))
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            logger.info("Successful: " + request.getMethodValue() + "-" + request.getPath());
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Failed: ( "+ err + ") " + request.getMethodValue() + "-" + request.getPath());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        String subject = null;
        try {
            subject = Jwts.parser()
                    .setSigningKey(environment.getProperty("token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty())
            returnValue = false;

        return returnValue;

    }

    public static class Config {
        // Put configration properties here

    }

}
