package br.com.nunius.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepositoryInterface extends JpaRepository<TaskModel, UUID> {
    
}
