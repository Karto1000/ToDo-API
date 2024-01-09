package ch.bbzsogr.todo.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * An Exception that can be thrown in any Route
 * This Exception will be caught by the GlobalExceptionHandler and transformed into a JSON body in the response
 */
@AllArgsConstructor
public class RouteException extends Exception {
    public String message;
    public HttpStatus status;
}
