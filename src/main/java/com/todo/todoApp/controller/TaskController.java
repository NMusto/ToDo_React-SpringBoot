package com.todo.todoApp.controller;

import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.entity.TaskStatus;
import com.todo.todoApp.service.TaskService;
import com.todo.todoApp.service.dto.TaskInDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody TaskInDTO taskInDTO) {
        return this.taskService.createTask(taskInDTO);
    }

    @GetMapping
    public List<Task> findAlTasks() {
        return this.taskService.findAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTaskById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.findTaskById(id));
        }
        catch (Exception e) {
            throw new RuntimeException("Task not found!");
        }
    }

    @GetMapping("/find_by_status")
    public List<Task> findAllByTaskStatus(@RequestParam(value = "task_status") TaskStatus taskStatus) {
        return this.taskService.findAllByTaskStatus(taskStatus);
    }

    @PatchMapping("/update_task_finished/{id}")
    public ResponseEntity<?> updateTaskAsFinished(@PathVariable("id") Long id) {
        this.taskService.updateTaskAsFinished(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        try {
            taskService.deleteTaskById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

