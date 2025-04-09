package com.todo.todoApp.Projections;

public interface ITaskOutProjection {
    Long getId();
    String getTitle();
    Boolean getFinished();
}
