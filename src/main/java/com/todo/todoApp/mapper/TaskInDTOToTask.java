package com.todo.todoApp.mapper;

import com.todo.todoApp.persistence.entity.Task;
import com.todo.todoApp.service.dto.TaskInDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskInDTOToTask implements IMapper<TaskInDTO, Task>{

    @Override
    public Task map(TaskInDTO in) {
        Task task = Task.builder()
                .title(in.getTitle())
                .finished(false)
                .build();

        return task;
    }
}
