package ch.bbzsogr.todo.repository;

import ch.bbzsogr.todo.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Iterable<Task> getByCompleted(boolean completed);

    @Modifying
    @Query("UPDATE Task t SET t.completed = true WHERE t.taskId = :id")
    void checkTaskById(@Param("id") Integer id);
}
