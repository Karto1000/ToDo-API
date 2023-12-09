package ch.bbzsogr.todo.repository;

import ch.bbzsogr.todo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> getUserByEmail(String email);
}
