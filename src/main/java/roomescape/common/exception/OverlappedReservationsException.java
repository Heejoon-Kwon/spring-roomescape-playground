package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class OverlappedReservationsException extends BusinessException {
    public OverlappedReservationsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
