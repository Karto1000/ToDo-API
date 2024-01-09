package ch.bbzsogr.todo.repository;

import ch.bbzsogr.todo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * A Repository containing CRUD Operations to access the app_task table
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Get a user by his email
     *
     * @param email The email to filter by
     * @return An Optional of a User which is empty if the user is not found with the specified email
     */
    Optional<User> getUserByEmail(String email);
}
