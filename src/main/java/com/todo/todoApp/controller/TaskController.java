package com.todo.todoApp.controller;

import com.todo.todoApp.Projections.ITaskOutProjection;
import com.todo.todoApp.exceptions.InfoException;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.entity.TaskStatus;
import com.todo.todoApp.service.TaskService;
import com.todo.todoApp.service.dto.TaskInDTO;
import com.todo.todoApp.service.dto.TaskOutDTO;
import com.todo.todoApp.service.dto.UpdateTaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskInDTO taskInDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskService.createTask(taskInDTO));
    }

    @GetMapping
    public ResponseEntity<List<ITaskOutProjection>> findAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTaskById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findTaskById(id));
    }

    @GetMapping("/find_all_by_finished")
    public ResponseEntity<List<ITaskOutProjection>> findAllByFinished (@RequestParam(value = "isFinished") Boolean isFinished) {
        return ResponseEntity.ok(taskService.findAllByFinished(isFinished));
    }

//    @GetMapping("/find_by_status")
//    public ResponseEntity<List<ITaskOutProjection>> findAllByTaskStatus(@RequestParam(value = "task_status") TaskStatus taskStatus) {
//        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findAllByTaskStatus(taskStatus));
//    }

    @PatchMapping("/update_task_finished/{id}")
    public ResponseEntity<?> updateTaskFinished(@PathVariable("id") Long id, @RequestParam(value = "key") Boolean key) {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.updateTaskFinished(id, key));
    }

    @PutMapping("/update_task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDTO updateTaskDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, updateTaskDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTaskById(id));
    }
}

