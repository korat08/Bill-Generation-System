package com.example.Bill_Generation_System.Config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class    GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException (MethodArgumentNotValidException e){

        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField()+ " : " + error.getDefaultMessage())
                .collect(Collectors.joining(" , "));

        Map<String,Object> error = new HashMap<>();

        error.put("error", true);
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("message", errorMessage);

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException e ) {

        Map<String, Object> error = new HashMap<>();

        error.put("error", true);
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", e.getMessage());

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception e) {

        Map<String, Object> error = new HashMap<>();

        error.put("error", true);
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", "Internal Server Error: " + e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
