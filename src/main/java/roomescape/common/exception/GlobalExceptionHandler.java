package roomescape.common.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ResponseEntity<ErrorResponseBody> handleNoReservationTime(NoSuchReservationTimeException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleEmptyResultDataAccess(EmptyResultDataAccessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
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

