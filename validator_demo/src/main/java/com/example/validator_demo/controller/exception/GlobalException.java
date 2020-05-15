package com.example.validator_demo.controller.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handler(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldError().getDefaultMessage();
    }

    @ExceptionHandler(JsonParseException.class)
    public String json(JsonParseException e) {
        return e.getMessage();
    }
}
