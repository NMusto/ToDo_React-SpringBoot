package com.todo.todoApp.service.dto;


import com.todo.todoApp.persistence.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskOutDTO {
    private String title;
    private String description;
    private LocalDateTime eta;
    private Boolean finished;
    private TaskStatus taskStatus;
}
