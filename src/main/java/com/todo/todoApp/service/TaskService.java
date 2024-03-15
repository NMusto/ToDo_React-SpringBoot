package com.todo.todoApp.service;

import com.todo.todoApp.exceptions.InfoException;
import com.todo.todoApp.mapper.TaskInDTOToTask;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.entity.TaskStatus;
import com.todo.todoApp.persistence.repository.TaskRepository;
import com.todo.todoApp.service.dto.TaskInDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskInDTOToTask taskInDTOToTask;

    public TaskService(TaskRepository taskRepository, TaskInDTOToTask taskInDTOToTask) {
        this.taskRepository = taskRepository;
        this.taskInDTOToTask = taskInDTOToTask;
    }

   public Task createTask(TaskInDTO taskInDTO) {
        Task task = taskInDTOToTask.map(taskInDTO);
        return this.taskRepository.save(task);
   }

   public List<Task> findAllTasks() {
        return this.taskRepository.findAll();
   }

    public Task findTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        return optionalTask.get();
    }

   public List<Task> findAllByTaskStatus(TaskStatus taskStatus) {
        List<Task> taskList = this.taskRepository.findAllByTaskStatus(taskStatus);
        if (taskList.isEmpty()) {
            throw new InfoException("There are no tasks for this state!", HttpStatus.NOT_FOUND);
        }
        return taskList;
   }

   @Transactional
   public String updateTaskFinished(Long id, boolean key) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        taskRepository.updateTaskFinished(id, key);
        return "Finished was successfully changed to: " + key;
   }

   public Task updateTask(Long id, TaskInDTO taskInDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
       Task task = optionalTask.get();
       task.setTitle(taskInDTO.getTitle());
       task.setDescription(taskInDTO.getDescription());
       task.setEta(taskInDTO.getEta());
       return taskRepository.save(task);
   }

   public String deleteTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return "Task deleted!";
   }
}
