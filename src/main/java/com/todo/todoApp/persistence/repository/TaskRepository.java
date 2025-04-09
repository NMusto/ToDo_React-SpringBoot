package com.todo.todoApp.persistence.repository;

import com.todo.todoApp.Projections.ITaskOutProjection;
import com.todo.todoApp.persistence.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query(value = "update task set finished=:key where id=:id", nativeQuery = true)
    void updateTaskFinished(@Param("id") Long id, @Param("key") Boolean key);

    List<ITaskOutProjection> findAllBy();

    List<ITaskOutProjection> findAllByFinished(Boolean isFinished);

}
