package ch.bbzsogr.todo.controller;

import ch.bbzsogr.todo.model.Task;
import ch.bbzsogr.todo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Transactional
@CrossOrigin("*")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/get/all")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        return new ResponseEntity<>(taskRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/pending")
    public ResponseEntity<Iterable<Task>> getPendingTasks() {
        return new ResponseEntity<>(taskRepository.getByCompleted(false), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Iterable<Task>> addTask(@RequestBody Task task) {
        taskRepository.save(task);
        return new ResponseEntity<>(taskRepository.getByCompleted(false), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        if (taskRepository.findById(task.getTaskId()).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", task.getTaskId()),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.save(task);
        return new ResponseEntity<>(taskRepository.getByCompleted(false), HttpStatus.OK);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<?> checkTaskById(@PathVariable Integer id) {
        if (taskRepository.findById(id).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", id),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.checkTaskById(id);
        return new ResponseEntity<>(taskRepository.getByCompleted(false), HttpStatus.OK);
    }

    @PutMapping(value = "/check", consumes = "application/json")
    public ResponseEntity<?> checkTask(@RequestBody Task task) {
        if (taskRepository.findById(task.getTaskId()).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", task.getTaskId()),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.checkTaskById(task.getTaskId());
        return new ResponseEntity<>(taskRepository.getByCompleted(false), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer id) {
        if (taskRepository.findById(id).isEmpty()) return new ResponseEntity<>(
                String.format("No Task with id %d", id),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.deleteById(id);
        return new ResponseEntity<>(taskRepository.findAll(), HttpStatus.OK);
    }
}
