package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidDateOrTimeFormatException extends BusinessException {
    public InvalidDateOrTimeFormatException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
