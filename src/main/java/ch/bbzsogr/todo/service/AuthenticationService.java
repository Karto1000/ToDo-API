package ch.bbzsogr.todo.service;

import ch.bbzsogr.todo.authentication.LoginRequestDao;
import ch.bbzsogr.todo.authentication.RegisterRequestDao;
import ch.bbzsogr.todo.configuration.SecurityConfiguration;
import ch.bbzsogr.todo.model.Role;
import ch.bbzsogr.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * A Spring boot service which handles the Authentication of Users
 */
@Service
public class AuthenticationService {
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Register a new User
     *
     * @param dao The DAO containing information about the user
     */
    public void register(RegisterRequestDao dao) {
        User.builder()
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
    }

    /**
     * Login with the specified user data
     *
     * @param dao The DAO containing information about the user
     * @return The Authentication object
     * @throws AuthenticationException If the Authentication fails
     */
    public Authentication login(LoginRequestDao dao) throws AuthenticationException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dao.getEmail(),
                        dao.getPassword()
                )
        );
    }
}
