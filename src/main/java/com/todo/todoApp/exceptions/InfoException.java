package com.todo.todoApp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InfoException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public InfoException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
