package br.com.nunius.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepositoryInterface taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel,
            HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartingAt()) || currentDate
                .isAfter(taskModel.getEndingAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("starting/ending date must be in the future");
        }
        if (taskModel.getStartingAt().isAfter(taskModel.getEndingAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("end date must be posterior to start");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
