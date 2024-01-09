package ch.bbzsogr.todo.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class RouteException extends Exception {
    public String message;
    public HttpStatus status;
}
