package ch.bbzsogr.todo.service;

import ch.bbzsogr.todo.authentication.LoginRequestDao;
import ch.bbzsogr.todo.authentication.RegisterRequestDao;
import ch.bbzsogr.todo.configuration.SecurityConfiguration;
import ch.bbzsogr.todo.model.Role;
import ch.bbzsogr.todo.model.User;
import ch.bbzsogr.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(RegisterRequestDao dao) {
        User user = User.builder()
                .email(dao.getEmail())
                .lastname(dao.getLastname())
                .firstname(dao.getFirstname())
                .password(
                        securityConfiguration
                                .passwordEncoder()
                                .encode(dao.getPassword())
                )
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public Authentication login(LoginRequestDao dao) throws AuthenticationException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dao.getEmail(),
                        dao.getPassword()
                )
        );
    }
}
