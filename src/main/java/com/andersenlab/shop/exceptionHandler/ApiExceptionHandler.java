package com.andersenlab.shop.exceptionHandler;

import com.andersenlab.shop.annotation.LoggingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @LoggingException
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleApiRequestException(Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getClass().getName(), e.getMessage(), e, badRequest, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badRequest);
    }

}
