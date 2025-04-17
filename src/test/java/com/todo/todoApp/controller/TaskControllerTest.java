package com.todo.todoApp.controller;

import com.todo.todoApp.Projections.ITaskOutProjection;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.repository.TaskRepository;
import com.todo.todoApp.service.TaskService;
import com.todo.todoApp.service.dto.TaskInDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask_ReturnsCreatedTask() throws Exception {

        String requestBody = """
                {
                    "title": "Task 1",
                    "finished": false
                }
                """;

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.finished").value(false));

        List<ITaskOutProjection> tasks = taskRepository.findAllBy();
        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
    }

    @Test
    void findAllTasks_ReturnsListOfTasks() throws Exception {

        Task task1 = Task.builder()
                .title("Task 1")
                .finished(true)
                .build();

        Task task2 = Task.builder()
                .title("Task 2")
                .finished(false)
                .build();

        taskRepository.saveAll(List.of(task1, task2));

        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].finished").value(false));
    }

    @Test
    void findTaskById_WhenExists() throws Exception {

        Task task1 = Task.builder()
                .title("Task 1")
                .finished(true)
                .build();
        Task savedTask = taskRepository.save(task1);

        mockMvc.perform(get("/api/task/" + savedTask.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.finished").value(true));
    }

    @Test
    void findTaskById_ReturnsNotFound_WhenTaskDoesNotExist() throws Exception {

        mockMvc.perform(get("/api/task/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTask_WhenExists() throws Exception {

        Task existingTask = Task.builder()
                .title("Title")
                .finished(true)
                .build();
        taskRepository.save(existingTask);

        Long id = existingTask.getId();

        String updatedContent = """
                {
                    "title": "Updated title"
                }
                """;

        mockMvc.perform(put("/api/task/update_task/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated title"));
    }

    @Test
    void updateTask_ReturnsNotFound_WhenTaskDoesNotExist() throws Exception {

        String requestBody = """
                {
                    "title": "Updated title"
                }
                """;

        mockMvc.perform(put("/api/task/update_task/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTaskById_WhenExists() throws Exception{

        Task existingTask = Task.builder()
                .title("Title")
                .finished(true)
                .build();
        existingTask = taskRepository.save(existingTask);

        Long id = existingTask.getId();

        mockMvc.perform(delete("/api/task/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted!"));
    }

    @Test
    void deleteTaskById_ReturnsNotFound_WhenTaskDoesNotExist() throws Exception {

        mockMvc.perform(delete("/api/task/delete/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTaskFinished_WhenExists() throws Exception {

        Task task = Task.builder()
                .title("Task 1")
                .finished(false)
                .build();
        taskRepository.save(task);
        Long id = task.getId();

        mockMvc.perform(patch("/api/task/update_task_finished/" + id)
                .param("key", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Finished was successfully changed to: true"));

        Optional<Task> updatedTask = taskRepository.findById(id);
        assertTrue(updatedTask.isPresent());
        assertTrue(updatedTask.get().getFinished());
    }

    @Test
    void updateTaskFinished_ReturnsNotFound_WhenTaskDoesNotExist() throws Exception {

        mockMvc.perform(patch("/api/task/update_task_finished/9999")
                .param("key", "false"))
                .andExpect(status().isNotFound());
    }
}
