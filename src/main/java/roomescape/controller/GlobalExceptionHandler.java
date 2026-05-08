package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.InvalidDateOrTimeFormatException;
import roomescape.exception.OverlappedReservationsException;
import roomescape.exception.RequestParameterMissingException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestParameterMissingException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameter(RuntimeException e) {
        return createExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoElementToDelete(RuntimeException e) {
        return createExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OverlappedReservationsException.class)
    public ResponseEntity<Map<String, Object>> handleOverlappedReservations(RuntimeException e) {
        return createExceptionResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDateOrTimeFormatException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDateTime(RuntimeException e) {
        return createExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> createExceptionResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, status);
    }
}

