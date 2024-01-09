package ch.bbzsogr.todo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * An ExceptionHandler which applies to every Route in every handler
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * An Override of the default handleMethodArgumentNotValid function of the base Class.
     * This function should return a more formatted version with more details
     *
     * @param ex      The thrown exception
     * @param headers The headers of the response
     * @param status  The status of the response
     * @param request The request that is being responded to
     * @return The formatted error response
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(e -> errorMessage.append(String.format("%s, ", e.getDefaultMessage())));
        // Remove trailing comma
        errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
        ApiError apiError = new ApiError(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle all RouteExceptions that are thrown
     *
     * @param ex The thrown route exception
     * @return The Json representation of the thrown ApiError
     */
    @ExceptionHandler(value = {RouteException.class})
    public ResponseEntity<ApiError> handleApiException(RouteException ex) {
        return new ResponseEntity<>(
                ApiError.builder().message(ex.message).status(ex.status).build(),
                null,
                ex.status
        );
    }
}
