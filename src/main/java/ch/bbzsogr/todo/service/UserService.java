package ch.bbzsogr.todo.service;

import ch.bbzsogr.todo.model.User;
import ch.bbzsogr.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * A User service that can fetch a user by his username
 *
 * <p>
 * Note: The username referred to in the function is actually the E-Mail of the user
 * </p>
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Load a user by his username
     *
     * @param username The username to search for
     * @return The User represented as a UserDetails object
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmail(username);
        return user.orElse(null);
    }
}
