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

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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
