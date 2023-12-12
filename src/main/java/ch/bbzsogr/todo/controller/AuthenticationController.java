package ch.bbzsogr.todo.controller;

import ch.bbzsogr.todo.authentication.LoginRequestDao;
import ch.bbzsogr.todo.authentication.RegisterRequestDao;
import ch.bbzsogr.todo.model.User;
import ch.bbzsogr.todo.service.AuthenticationService;
import ch.bbzsogr.todo.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequestDao registerRequestDao) {
        authenticationService.register(registerRequestDao);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDao loginRequestDao) {
        try {
            Authentication authentication = authenticationService.login(loginRequestDao);
            String token = jwtService.generateToken((User) authentication.getPrincipal());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
