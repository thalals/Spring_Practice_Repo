package com.example.springcore.exeception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
}