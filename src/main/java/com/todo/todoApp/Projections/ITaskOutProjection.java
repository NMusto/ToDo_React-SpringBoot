package com.todo.todoApp.Projections;

import com.todo.todoApp.persistence.entity.TaskStatus;

import java.time.LocalDateTime;

public interface ITaskOutProjection {
    Long getId();
    String getTitle();
    Boolean getFinished();
}
