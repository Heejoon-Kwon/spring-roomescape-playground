package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.InvalidDateOrTimeFormatException;
import roomescape.exception.NoSuchElementToDeleteException;
import roomescape.exception.OverlappedReservationsException;
import roomescape.exception.RequestParameterMissingException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleMissingParameter(RequestParameterMissingException e) {
        return e.createExceptionResponse();
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleNoElementToDelete(NoSuchElementToDeleteException e) {
        return e.createExceptionResponse();
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleOverlappedReservations(OverlappedReservationsException e) {
        return e.createExceptionResponse();
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleInvalidDateTime(InvalidDateOrTimeFormatException e) {
        return e.createExceptionResponse();
    }
}

