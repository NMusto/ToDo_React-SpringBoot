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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskInDTOToTask taskInDTOToTask;
    private final TaskToOutDTO taskToOutDTO;

    public TaskService(TaskRepository taskRepository, TaskInDTOToTask taskInDTOToTask, TaskToOutDTO taskToOutDTO) {
        this.taskRepository = taskRepository;
        this.taskInDTOToTask = taskInDTOToTask;
        this.taskToOutDTO = taskToOutDTO;
    }

   public Task createTask(TaskInDTO taskInDTO) {
        Task task = taskInDTOToTask.map(taskInDTO);
        return this.taskRepository.save(task);
   }

   public List<ITaskOutProjection> findAllTasks() {
        return this.taskRepository.findAllBy();
   }

    public TaskOutDTO findTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        TaskOutDTO taskOutDTO = taskToOutDTO.map(optionalTask.get());
        return taskOutDTO;
    }


   @Transactional
   public String updateTaskFinished(Long id, Boolean key) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        taskRepository.updateTaskFinished(id, key);
        return "Finished was successfully changed to: " + key;
   }


   public TaskOutDTO updateTask(Long id, UpdateTaskDTO updateTaskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
       Task task = optionalTask.get();
       task.setTitle(updateTaskDTO.getTitle());
       taskRepository.save(task);
       return taskToOutDTO.map(task);
   }

   public String deleteTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return "Task deleted!";
   }

   public List<ITaskOutProjection> findAllByFinished (Boolean isFinished) {
        return taskRepository.findAllByFinished(isFinished);
   }
}
