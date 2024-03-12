package com.todo.todoApp.service;

import com.todo.todoApp.mapper.TaskInDTOToTask;
import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.persistence.entity.TaskStatus;
import com.todo.todoApp.persistence.repository.TaskRepository;
import com.todo.todoApp.service.dto.TaskInDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

   public List<Task> findAllByTaskStatus(TaskStatus taskStatus) {
        return this.taskRepository.findAllByTaskStatus(taskStatus);
   }

   @Transactional
   public void updateTaskAsFinished(Long id) {
        taskRepository.updateTaskAsFinished(id);
   }


}
