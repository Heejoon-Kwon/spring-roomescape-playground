package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleMissingParameter(RequestParameterMissingException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleNoElementToDelete(NoSuchElementToDeleteException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleOverlappedReservations(OverlappedReservationsException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleInvalidDateTime(InvalidDateOrTimeFormatException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }
}

