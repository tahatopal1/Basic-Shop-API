package com.example.productws.security;

import com.example.productws.service.IntegrationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.logging.Logger;

public class GeneralPurposeInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(getClass().getName());

    private IntegrationService integrationService;

    public GeneralPurposeInterceptor(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle method of GeneralPurposeInterceptor");
        if (Strings.isNotEmpty(request.getHeader("Authorization"))){
            ResponseEntity responseEntity = integrationService.validateAuthenticationToken(Map.of("Authorization", request.getHeader("Authorization")));
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return true;
            return setResponseAndReturn(response, responseEntity.getStatusCode());
        }else{
            return setResponseAndReturn(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle method of GeneralPurposeInterceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion method of GeneralPurposeInterceptor");
    }

    private boolean setResponseAndReturn(HttpServletResponse response, HttpStatus httpStatus){
        response.setStatus(httpStatus.value());
        return false;
    }

}
