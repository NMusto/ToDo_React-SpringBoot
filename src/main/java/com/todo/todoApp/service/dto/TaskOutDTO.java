package com.todo.todoApp.service.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskOutDTO {
    private String title;
    private Boolean finished;
}
