package ch.bbzsogr.todo.repository;

import ch.bbzsogr.todo.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Iterable<Task> getByCreatedBy(int createdBy);

    @Query("SELECT t FROM Task t WHERE t.createdBy = :createdBy AND t.completed = :completed")
    Iterable<Task> getByCompleted(int createdBy, boolean completed);

    @Query("SELECT t FROM Task t WHERE t.taskId = :id AND t.createdBy = :createdBy")
    Optional<Task> getById(int id, int createdBy);

    @Modifying
    @Query("UPDATE Task t SET t.completed = true WHERE t.taskId = :id")
    void checkTaskById(@Param("id") Integer id);
}
