package com.project.handler;

import com.project.dto.GeneralErrorDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorDTO> handleAnyException(Exception e){
        GeneralErrorDTO generalError = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(generalError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidException> handleValidationException(MethodArgumentNotValidException e){
        GeneralErrorDTO generalError = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getAllErrors().get(0).getDefaultMessage(), System.currentTimeMillis());
        return new ResponseEntity(generalError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GeneralErrorDTO> handleRuntimeException(RuntimeException e){
        GeneralErrorDTO generalError = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(generalError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<GeneralErrorDTO> handleSQLException(DataIntegrityViolationException e){
        GeneralErrorDTO generalError = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), "This e-mail has already been taken!", System.currentTimeMillis());
        return new ResponseEntity<>(generalError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
