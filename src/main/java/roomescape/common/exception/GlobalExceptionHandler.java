package roomescape.common.exception;

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

