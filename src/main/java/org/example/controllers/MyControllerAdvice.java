package org.example.controllers;

import org.example.exceptions.RoleNotFoundException;
import org.example.exceptions.UserLibraryNotFoundException;
import org.example.util.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(RoleNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(UserLibraryNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(UserLibraryNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }
}
