package com.todo.todoApp.mapper;

import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.service.dto.TaskOutDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskToOutDTO implements IMapper<Task, TaskOutDTO> {

    @Override
    public TaskOutDTO map(Task task) {
        TaskOutDTO taskOutDTO = new TaskOutDTO();

        taskOutDTO.setTitle(task.getTitle());
        taskOutDTO.setFinished(task.getFinished());

        return taskOutDTO;
    }
}
