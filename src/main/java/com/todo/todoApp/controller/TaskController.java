package com.todo.todoApp.controller;

import com.todo.todoApp.Projections.ITaskOutProjection;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.service.TaskService;
import com.todo.todoApp.service.dto.TaskInDTO;
import com.todo.todoApp.service.dto.TaskOutDTO;
import com.todo.todoApp.service.dto.UpdateTaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Create a new task ",
            description = "Registers a new task in the system using the provided task data"
    )
    public ResponseEntity<Task> createTask (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data required to create a task",
                    required = true
            )
            @RequestBody TaskInDTO taskInDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskService.createTask(taskInDTO));
    }


    @GetMapping
    @Operation(
            summary = "Get all tasks",
            description = "Retrieves a list of all task in the system"
    )
    public ResponseEntity<List<ITaskOutProjection>> findAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findAllTasks());
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Get task by id",
            description = "Retrieves the details of a specific task using its id"
    )
    public ResponseEntity<TaskOutDTO> findTaskById (
            @Parameter(
                    description = "Id of the task to retrieve",
                    required = true
            )
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findTaskById(id));
    }


    @GetMapping("/find_all_by_finished")
    @Operation(
            summary = "Get all tasks by status",
            description = "Retrieves a list of tasks filtered by isFinished status"
    )
    public ResponseEntity<List<ITaskOutProjection>> findAllByFinished (
            @Parameter(
                    description = "Filter by task status. Use true for completed tasks, false for pending tasks ",
                    required = true,
                    example = "true"
            )
            @RequestParam(value = "isFinished") Boolean isFinished) {
        return ResponseEntity.ok(taskService.findAllByFinished(isFinished));
    }


    @PatchMapping("/update_task_finished/{id}")
    @Operation(
            summary = "Update task status",
            description = "Allows changing the 'isFinished' status of a task by its id"
    )
    public ResponseEntity<String> updateTaskFinished (
            @Parameter(
                    description = "Id of the task to be updated",
                    required = true,
                    example = "1"
            )
            @PathVariable("id") Long id,

            @Parameter(
                    description = "New finished status of the task (true or false)",
                    required = true,
                    example = "true"
            )
            @RequestParam(value = "key") Boolean key) {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.updateTaskFinished(id, key));
    }



    @PutMapping("/update_task/{id}")
    @Operation(
            summary = "Update an existing task",
            description = "Updates the details of a task by its id using the provided data in the request body"
    )
    public ResponseEntity<TaskOutDTO> updateTask(
            @Parameter(
                    description = "Id of the task to be updated",
                    required = true,
                    example = "2"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task fields to update",
                    required = true
            )
            @RequestBody UpdateTaskDTO updateTaskDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, updateTaskDTO));
    }


    @DeleteMapping("/delete/{id}")
    @Operation(
        summary = "Delete a task by id",
        description = "Deletes de task from the system using its id"
    )
    public ResponseEntity<String> deleteTaskById(
            @Parameter(
                    description = "Id of the task to be deleted",
                    required = true
            )
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTaskById(id));
    }
}

