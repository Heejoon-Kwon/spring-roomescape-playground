package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.controller.GlobalExceptionHandler;
import roomescape.exception.InvalidDateOrTimeFormatException;
import roomescape.exception.OverlappedReservationsException;
import roomescape.exception.RequestParameterMissingException;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlingTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void missingParameterExceptionCreatesBadRequestResponse() {
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleMissingParameter(
                new RequestParameterMissingException("name")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .containsEntry("message", "name is missing.")
                .containsKey("timestamp");
    }

    @Test
    void invalidDateOrTimeExceptionCreatesBadRequestResponse() {
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleInvalidDateTime(
                new InvalidDateOrTimeFormatException("Date or Time has invalid format.")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .containsEntry("message", "Date or Time has invalid format.")
                .containsKey("timestamp");
    }

    @Test
    void overlappedReservationsExceptionCreatesConflictResponse() {
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleOverlappedReservations(
                new OverlappedReservationsException("Reservations overlap")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody())
                .containsEntry("message", "Reservations overlap")
                .containsKey("timestamp");
    }

    @Test
    void noSuchElementExceptionCreatesNotFoundResponse() {
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNoElementToDelete(
                new NoSuchElementException("No such a reservation with id 999")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .containsEntry("message", "No such a reservation with id 999")
                .containsKey("timestamp");
    }
}
