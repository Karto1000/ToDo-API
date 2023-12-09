package ch.bbzsogr.todo.controller;

import ch.bbzsogr.todo.authentication.*;
import ch.bbzsogr.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<User> register(@Validated @RequestBody RegisterRequestDao registerRequestDao) {
        return new ResponseEntity<>(authenticationService.register(registerRequestDao), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequestDao loginRequestDao) {
        try {
            return new ResponseEntity<>(authenticationService.login(loginRequestDao), HttpStatus.OK);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }
}
