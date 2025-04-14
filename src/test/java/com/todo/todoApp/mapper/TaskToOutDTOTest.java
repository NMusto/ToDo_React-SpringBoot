package com.todo.todoApp.mapper;

import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.service.dto.TaskOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskToOutDTOTest {

    private final TaskToOutDTO taskToOutDTO = new TaskToOutDTO();

    @Test
    void testMap() {

        // Arrange
        Long id = 1L;

        Task task = Task.builder()
                .id(id)
                .title("Test title")
                .finished(true)
                .build();

        // Act
        TaskOutDTO taskOutDTO = taskToOutDTO.map(task);

        // Assert
        assertNotNull(taskOutDTO);
        assertEquals("Test title", taskOutDTO.getTitle());
        assertTrue(taskOutDTO.getFinished());

    }
}
