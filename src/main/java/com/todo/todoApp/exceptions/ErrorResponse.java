package com.todo.todoApp.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
}
