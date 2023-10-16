package br.com.nunius.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;

    private LocalDateTime startingAt;
    private LocalDateTime endingAt;

    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID userId;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("title must have a maximum of 50 characters");
        }
        this.title = title;
    }
}
