package ch.bbzsogr.todo.repository;

import ch.bbzsogr.todo.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A Repository containing CRUD Operations to access the app_task table
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    /**
     * Get all tasks that have been created by the specified user
     *
     * @param createdBy The user to filter by
     * @return A list of all tasks which the specified user created
     */
    Iterable<Task> getByCreatedBy(int createdBy);

    /**
     * Get all tasks that have the deleted field set to the passed parameter
     * Only the tasks that have been created by the user passed as the createdBy parameter
     *
     * @param createdBy The user that created the tasks
     * @param completed Specify which tasks should be returned dictated by the completed field
     * @return An iterable Interface containing all tasks that were selected
     */
    @Query("SELECT t FROM Task t WHERE t.createdBy = :createdBy AND t.completed = :completed")
    Iterable<Task> getByCompleted(int createdBy, boolean completed);

    /**
     * Get a task by its id
     * Only tasks that were created by the specified are returned
     *
     * @param id        The task id
     * @param createdBy The user to filter by
     * @return An Optional of a task which is empty if the task is not found
     */
    @Query("SELECT t FROM Task t WHERE t.taskId = :id AND t.createdBy = :createdBy")
    Optional<Task> getById(int id, int createdBy);

    /**
     * Check a task by its id
     * This sets the completed field to 'true'
     *
     * @param id The id of the task to check
     */
    @Modifying
    @Query("UPDATE Task t SET t.completed = true WHERE t.taskId = :id")
    void checkTaskById(@Param("id") Integer id);
}
