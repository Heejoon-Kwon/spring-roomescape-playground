package roomescape.common.exception;

import java.time.LocalDateTime;

public record ErrorResponseBody(String message, LocalDateTime timestamp) {
    public static ErrorResponseBody from(String message) {
        return new ErrorResponseBody(message, LocalDateTime.now());
    }
}
