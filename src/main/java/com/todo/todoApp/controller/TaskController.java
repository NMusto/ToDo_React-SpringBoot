package com.todo.todoApp.controller;

import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.entity.TaskStatus;
import com.todo.todoApp.service.TaskService;
import com.todo.todoApp.service.dto.TaskInDTO;
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

    @GetMapping("/{taskStatus}")
    public List<Task> findAllByTaskStatus(@PathVariable TaskStatus taskStatus) {
        return this.taskService.findAllByTaskStatus(taskStatus);
    }


}

