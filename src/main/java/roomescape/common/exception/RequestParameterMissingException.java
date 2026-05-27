package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class RequestParameterMissingException extends BusinessException {
    public RequestParameterMissingException(String missingParameter) {
        super(missingParameter+" is missing.", HttpStatus.BAD_REQUEST);
    }
}
