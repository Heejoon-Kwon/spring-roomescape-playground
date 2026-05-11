package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BusinessException extends RuntimeException {
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    private final HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public ResponseEntity<Map<String, Object>> createExceptionResponse() {
        Map<String, Object> body = new HashMap<>();
        body.put("message", getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, getStatus());
    }
}
