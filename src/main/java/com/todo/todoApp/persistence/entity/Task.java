package com.todo.todoApp.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Boolean finished;

//    private String description;
//    private LocalDateTime creationDate;
//    private LocalDateTime eta;
//
//    @Column(name = "task_status")
//    @Enumerated(EnumType.STRING)
//    private TaskStatus taskStatus;
}
