package ru.bank.validation.exception.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ServiceException extends RuntimeException {

    private static final Map<String, Object> EMPTY_INTERNAL_ERRORS = Map.of();

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public Map<String, Object> getInternalErrors() {
        return EMPTY_INTERNAL_ERRORS;
    }

    public List<Map<String, Object>> getAllInternalErrors() {
        return ExceptionUtils.getThrowableList(this).stream()
            .filter(ServiceException.class::isInstance)
            .map(ServiceException.class::cast)
            .map(ServiceException::getInternalErrors)
            .filter(internalErrors -> !internalErrors.isEmpty())
            .collect(Collectors.toList());
    }
}
