package com.todo.todoApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> GeneralExceptionHandler(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Internal Connection Error!", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InfoException.class)
    public ResponseEntity<ErrorResponse> InfoExceptionHandler(InfoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

}
