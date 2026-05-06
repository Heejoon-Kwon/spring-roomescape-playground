package roomescape.exception;

public class RequestParameterMissingException extends RuntimeException {
    public RequestParameterMissingException(String missingParameter) {
        super(missingParameter+" is missing.");
    }
}
