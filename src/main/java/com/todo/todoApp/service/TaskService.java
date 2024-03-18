package com.todo.todoApp.service;

import com.todo.todoApp.Projections.ITaskOutProjection;
import com.todo.todoApp.exceptions.InfoException;
import com.todo.todoApp.mapper.TaskInDTOToTask;
import com.todo.todoApp.mapper.TaskToOutDTO;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.entity.TaskStatus;
import com.todo.todoApp.persistence.repository.TaskRepository;
import com.todo.todoApp.service.dto.TaskInDTO;
import com.todo.todoApp.service.dto.TaskOutDTO;
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
        List<ITaskOutProjection> taskList = this.taskRepository.findAllBy();
       if (taskList.isEmpty()) {
           throw new InfoException("There are no task yet!", HttpStatus.NOT_FOUND);
       }
        return taskList;
   }

    public TaskOutDTO findTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
        TaskOutDTO taskOutDTO = taskToOutDTO.map(optionalTask.get());
        return taskOutDTO;
    }

   public List<ITaskOutProjection> findAllByTaskStatus(TaskStatus taskStatus) {
        List<ITaskOutProjection> taskList = this.taskRepository.findAllByTaskStatus(taskStatus);
        if (taskList.isEmpty()) {
            throw new InfoException("There are no tasks for this state!", HttpStatus.NOT_FOUND);
        }
        return taskList;
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

   public TaskOutDTO updateTask(Long id, TaskInDTO taskInDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new InfoException("Task not found!", HttpStatus.NOT_FOUND);
        }
       Task task = optionalTask.get();
       task.setTitle(taskInDTO.getTitle());
       task.setDescription(taskInDTO.getDescription());
       task.setEta(taskInDTO.getEta());
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
}
