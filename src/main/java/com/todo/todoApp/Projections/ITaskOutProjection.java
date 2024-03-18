package com.todo.todoApp.Projections;

import com.todo.todoApp.persistence.entity.TaskStatus;

import java.time.LocalDateTime;

public interface ITaskOutProjection {
    String getTitle();
    String getDescription();
    LocalDateTime getEta();
    Boolean getFinished();
    TaskStatus getTaskStatus();
}
