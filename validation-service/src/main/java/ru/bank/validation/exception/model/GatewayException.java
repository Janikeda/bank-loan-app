package ru.bank.validation.exception.model;

import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;

public class GatewayException extends ServiceException {

    private final HttpStatus httpStatus;
    private final Map<String, Object> errorBody;

    public GatewayException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = null;
        this.errorBody = Map.of();
    }

    public GatewayException(HttpStatus httpStatus, Map<String, Object> errorBody, String message,
        Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorBody = errorBody;
    }

    public Optional<HttpStatus> getHttpStatus() {
        return Optional.ofNullable(httpStatus);
    }

    @Override
    public Map<String, Object> getInternalErrors() {
        return errorBody;
    }
}
