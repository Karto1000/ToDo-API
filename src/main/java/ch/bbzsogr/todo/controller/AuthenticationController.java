package ch.bbzsogr.todo.controller;

import ch.bbzsogr.todo.authentication.LoginRequestDao;
import ch.bbzsogr.todo.authentication.RegisterRequestDao;
import ch.bbzsogr.todo.exceptions.RouteException;
import ch.bbzsogr.todo.model.User;
import ch.bbzsogr.todo.service.AuthenticationService;
import ch.bbzsogr.todo.service.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JWTService jwtService;

    /**
     * Create a new Account with the specified email and password
     *
     * @param registerRequestDao The Object with which the user will be created
     * @return A confirmation message that the user has been created
     * @throws RouteException If the Email is already Used, or if an exception occurs while the user is being created
     */
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDao registerRequestDao) throws RouteException {
        if (!registerRequestDao.getPassword().equals(registerRequestDao.getRepeatedPassword())) {
            throw new RouteException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        try {
            authenticationService.register(registerRequestDao);
            return new ResponseEntity<>("User Created", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new RouteException("Email Is already Used", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RouteException("Failed to create User", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login with the credentials of a registered user
     *
     * @param loginRequestDao The Object that contains the credentials to perform the login
     * @return The generated JWT Token
     * @throws RouteException If the email is not found or the password does not match, or if any other error occurs
     */
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDao loginRequestDao) throws RouteException {
        try {
            Authentication authentication = authenticationService.login(loginRequestDao);
            String token = jwtService.generateToken((User) authentication.getPrincipal());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException | RouteException e) {
            throw new RouteException("Email or Password is not Valid", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RouteException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
