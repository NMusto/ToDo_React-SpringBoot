package com.todo.todoApp.mapper;

import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.service.dto.TaskInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskInDTOToTaskTest {

    private final TaskInDTOToTask taskInDTOToTask = new TaskInDTOToTask();

    @Test
    void testMap() {

        // Arrange
        TaskInDTO taskInDTO = TaskInDTO.builder()
                .title("Test title")
                .build();

        // Act
        Task task = taskInDTOToTask.map(taskInDTO);

        // Assert
        assertNotNull(task);
        assertEquals("Test title", task.getTitle());
        assertFalse(task.getFinished());
    }
}
