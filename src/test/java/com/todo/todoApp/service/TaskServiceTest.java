package com.todo.todoApp.service;

import com.todo.todoApp.Projections.ITaskOutProjection;
import com.todo.todoApp.exceptions.InfoException;
import com.todo.todoApp.mapper.TaskInDTOToTask;
import com.todo.todoApp.mapper.TaskToOutDTO;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.repository.TaskRepository;
import com.todo.todoApp.service.dto.TaskInDTO;
import com.todo.todoApp.service.dto.TaskOutDTO;
import com.todo.todoApp.service.dto.UpdateTaskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskToOutDTO taskToOutDTO;

    @Mock
    private TaskInDTOToTask taskInDTOToTask;

    @InjectMocks
    private TaskService taskService;



    @Test
    void createTaskTest() {

        // Arrange
        TaskInDTO taskInDTO = new TaskInDTO();
        taskInDTO.setTitle("Test Task");

        Task mappedTask = new Task();
        mappedTask.setTitle("Test Task");

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Test Task");

        // Mocks
        when(taskInDTOToTask.map(taskInDTO)).thenReturn(mappedTask);
        when(taskRepository.save(mappedTask)).thenReturn(savedTask);

        // Act
        Task result = taskService.createTask(taskInDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
        verify(taskInDTOToTask).map(taskInDTO);
        verify(taskRepository).save(mappedTask);
    }

    @Test
    void findAllTasksTest() {

        // Arrange
        ITaskOutProjection projection1 = mock(ITaskOutProjection.class);
        ITaskOutProjection projection2 = mock(ITaskOutProjection.class);

        when(projection1.getId()).thenReturn(1L);
        when(projection1.getTitle()).thenReturn("Task 1");
        when(projection1.getFinished()).thenReturn(false);

        when(projection2.getId()).thenReturn(2L);
        when(projection2.getTitle()).thenReturn("Task 2");
        when(projection2.getFinished()).thenReturn(false);

        List<ITaskOutProjection> mockList = List.of(projection1, projection2);
        when(taskRepository.findAllBy()).thenReturn(mockList);

        // Act
        List<ITaskOutProjection> result = taskService.findAllTasks();

        // Assert
        assertNotNull(result);;
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Task 1", result.get(0).getTitle());
        assertFalse(result.get(0).getFinished());
        assertEquals(2, result.get(1).getId());
        assertEquals("Task 2", result.get(1).getTitle());
        assertFalse(result.get(1).getFinished());

        verify(taskRepository).findAllBy();

    }

    @Test
    void FindTaskByIdTest_ReturnsTaskOutDTO () {

        // Arrange
        Long id = 1L;

        Task task = Task.builder()
                .id(id)
                .title("Test task")
                .finished(false)
                .build();

        TaskOutDTO outDto = TaskOutDTO.builder()
            .title("Test task")
            .finished(false)
            .build();


        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskToOutDTO.map(task)).thenReturn(outDto);

        // Act
        TaskOutDTO result = taskService.findTaskById(id);

        // Assert
        assertNotNull(result);
        assertEquals("Test task", result.getTitle());
        assertFalse(result.getFinished());
        verify(taskRepository).findById(id);
        verify(taskToOutDTO).map(task);
    }

    @Test
    void FindTaskByIdTest_ThrowsInfoException_WhenTaskNotFound() {

        // Arrange
        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        InfoException exception = assertThrows(
                InfoException.class,
                () -> taskService.findTaskById(id)
        );

        assertEquals("Task not found!", exception.getMessage());
    }

    @Test
    void updateTaskFinishedTest_SuccessfulUpdate() {

        // Arrange
        Long id = 1L;
        Boolean key = true;

        Task task = Task.builder()
                .id(id)
                .title("Task 1")
                .finished(false)
                .build();

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        // Act
        String result = taskService.updateTaskFinished(id, key);

        // Assert
        assertEquals("Finished was successfully changed to: true", result);
        verify(taskRepository).findById(id);
        verify(taskRepository).updateTaskFinished(id, key);
    }


    @Test
    void updateTaskFinishedTest_ThrowsInfoException_WhenTaskNotFound() {

        // Arrange
        Long id = 1L;
        Boolean key = true;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        InfoException exception = assertThrows(
                InfoException.class,
                () -> taskService.updateTaskFinished(id, key)
        );

        assertEquals("Task not found!", exception.getMessage());
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).updateTaskFinished(anyLong(), anyBoolean());
    }

    @Test
    void updateTaskTest_SuccessfulUpdate() {

        // Arrange
        Long id = 1L;

        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setTitle("New title");

        Task task = Task.builder()
                .id(id)
                .title("Title")
                .build();

        TaskOutDTO taskOutDTO = TaskOutDTO.builder()
                .title("Updated title")
                .build();

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskToOutDTO.map(task)).thenReturn(taskOutDTO);

        // Act
        TaskOutDTO result = taskService.updateTask(id, updateTaskDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated title", result.getTitle());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(task);
        verify(taskToOutDTO).map(task);
    }

    @Test
    void updateTaskTest_ThrowsInfoException_WhenTaskNotFound() {

        // Arrange
        Long id = 1L;

        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setTitle("New title");

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        InfoException exception = assertThrows(
                InfoException.class,
                () -> taskService.updateTask(id, updateTaskDTO)
        );

        assertEquals("Task not found!", exception.getMessage());
        verify(taskRepository, never()).save(any());
        verify(taskToOutDTO, never()).map(any());
    }

    @Test
    void deleteTaskByIdTest_SuccessfulDelet() {

        // Arrange
        Long id = 1L;

        Task task = Task.builder()
                .id(id)
                .title("Task 1")
                .finished(false)
                .build();

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        // Act
        String result = taskService.deleteTaskById(id);

        // Assert
        assertEquals("Task deleted!", result);
        verify(taskRepository).findById(id);
        verify(taskRepository).deleteById(id);
    }

    @Test
    void deleteTaskByIdTest_ThrowsInfoException_WhenTaskNotFound() {

        // Arrange
        Long id = 1L;

        // Act and Assert
        InfoException exception = assertThrows(
                InfoException.class,
                () -> taskService.deleteTaskById(id)
        );

        assertEquals("Task not found!", exception.getMessage());
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).deleteById(anyLong());
    }

}
