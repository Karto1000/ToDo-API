package ch.bbzsogr.todo.controller;

import ch.bbzsogr.todo.exceptions.RouteException;
import ch.bbzsogr.todo.model.Task;
import ch.bbzsogr.todo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Transactional
@CrossOrigin("*")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Get all tasks that were created by the user that sent the request
     *
     * @return A list of the tasks the requesting user created
     */
    @GetMapping("/get/all")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(taskRepository.getByCreatedBy(user_id), HttpStatus.OK);
    }

    /**
     * Get all tasks that have their 'completed' flag set to false
     *
     * @return A list of the tasks with the 'completed' flag set to false
     */
    @GetMapping("/get/pending")
    public ResponseEntity<Iterable<Task>> getPendingTasks() {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    /**
     * Get a task by its id
     *
     * @param taskId The id of the task to get
     * @return The task with the specified id
     * @throws RouteException If the task is not found
     */
    @GetMapping("/get/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer taskId) throws RouteException {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        Optional<Task> optionalTask = taskRepository.getById(taskId, user_id);

        if (optionalTask.isEmpty())
            throw new RouteException(String.format("Task %s does not exist", taskId), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    /**
     * Create a new task owned by the requesting user
     *
     * @param task The task to create
     * @return A list of tasks which have not been completed
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Iterable<Task>> addTask(@RequestBody @Valid Task task) {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        task.setCreatedBy(user_id);

        taskRepository.save(task);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    /**
     * Update a task
     * A User can only edit the tasks that they have created
     *
     * @param task The task to edit
     * @return A list of tasks which have not been completed
     * @throws RouteException If the task with the id is not found
     */
    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<Iterable<Task>> updateTask(@RequestBody @Valid Task task) throws RouteException {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(task.getTaskId(), user_id).isEmpty()) throw new RouteException(
                String.format("No Task with id %d", task.getTaskId()),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.save(task);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    /**
     * Check a task by its id
     *
     * @param taskId The id of the task to check
     * @return A list of tasks which have not been completed
     * @throws RouteException If the task with the id is not found
     */
    @PutMapping("/check/{taskId}")
    public ResponseEntity<Iterable<Task>> checkTaskById(@PathVariable Integer taskId) throws RouteException {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(taskId, user_id).isEmpty()) throw new RouteException(
                String.format("No Task with id %d", taskId),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.checkTaskById(taskId);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    /**
     * Check a task by sending a json task in the body
     *
     * @param task The task to check
     * @return A list of tasks which have not been completed
     * @throws RouteException If the task with the id is not found
     */
    @PutMapping(value = "/check", consumes = "application/json")
    public ResponseEntity<Iterable<Task>> checkTask(@RequestBody @Valid Task task) throws RouteException {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(task.getTaskId(), user_id).isEmpty()) throw new RouteException(
                String.format("No Task with id %d", task.getTaskId()),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.checkTaskById(task.getTaskId());
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }

    /**
     * Delete a task by its id
     *
     * @param taskId The id of the task to delete
     * @return A list of tasks which have not been completed
     * @throws RouteException If the task with the id is not found
     */
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Iterable<Task>> deleteTaskById(@PathVariable Integer taskId) throws RouteException {
        int user_id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (taskRepository.getById(taskId, user_id).isEmpty()) throw new RouteException(
                String.format("No Task with id %d", taskId),
                HttpStatus.BAD_REQUEST
        );

        taskRepository.deleteById(taskId);
        return new ResponseEntity<>(taskRepository.getByCompleted(user_id, false), HttpStatus.OK);
    }
}
