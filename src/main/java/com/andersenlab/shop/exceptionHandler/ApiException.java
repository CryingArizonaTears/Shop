package com.andersenlab.shop.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
public class ApiException {
    private String name;
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;
}
