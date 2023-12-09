package ch.bbzsogr.todo.authentication;

import ch.bbzsogr.todo.model.Role;
import ch.bbzsogr.todo.model.User;
import ch.bbzsogr.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    public User register(RegisterRequestDao dao) {
        User user = new User();
        user.setEmail(dao.getEmail());
        user.setLastname(dao.getLastname());
        user.setFirstname(dao.getFirstname());
        user.setPassword(dao.getPassword());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public String login(LoginRequestDao dao) throws UserNotFoundException, InvalidPasswordException {
        Optional<User> optionalUser = userRepository.getUserByEmail(dao.getEmail());

        if (optionalUser.isEmpty()) throw new UserNotFoundException();

        User user = optionalUser.get();

        if (!user.getPassword().equals(dao.getPassword())) throw new InvalidPasswordException();

        return "Login Successful";
    }
}
