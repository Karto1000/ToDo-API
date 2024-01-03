package ch.bbzsogr.todo.controller;

import ch.bbzsogr.todo.model.Task;
import ch.bbzsogr.todo.repository.TaskRepository;
import ch.bbzsogr.todo.service.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Transactional
@CrossOrigin("*")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllTasks() {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(taskRepository.getByCreatedBy(user_id), HttpStatus.OK);
    }

    @GetMapping("/get/pending")
    public ResponseEntity<?> getPendingTasks() {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    @GetMapping("/get/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer taskId) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        Optional<Task> optionalTask = taskRepository.getById(taskId, user_id);

        if (optionalTask.isEmpty()) return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        task.setCreatedBy(user_id);

        taskRepository.save(task);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.findById(task.getTaskId()).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with user_id %d", task.getTaskId()),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.save(task);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    @PutMapping("/check/{taskId}")
    public ResponseEntity<?> checkTaskById(@PathVariable Integer taskId) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(taskId, user_id).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", taskId),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.checkTaskById(taskId);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    @PutMapping(value = "/check", consumes = "application/json")
    public ResponseEntity<?> checkTask(@RequestBody Task task) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(task.getTaskId(), user_id).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", task.getTaskId()),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.checkTaskById(task.getTaskId());
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer taskId) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(taskId, user_id).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", taskId),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.deleteById(taskId);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }
}
