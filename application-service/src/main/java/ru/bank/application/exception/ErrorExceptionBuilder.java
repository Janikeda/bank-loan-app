package ru.bank.application.exception;

import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.bank.application.exception.model.ErrorDescription;

@Component
public class ErrorExceptionBuilder {

    private final String serviceName;

    public ErrorExceptionBuilder(@Value("${info.app.name}") String serviceName) {
        this.serviceName = serviceName;
    }

    public ErrorDescription build(HttpStatus httpStatus, Exception exception) {
        var errorDescription = new ErrorDescription();
        errorDescription.setServiceName(serviceName);
        errorDescription.setErrorCode(String.valueOf(httpStatus.value()));
        String userMessage;
        if (exception instanceof ConstraintViolationException) {
            userMessage = ((ConstraintViolationException) exception).getConstraintViolations()
                .stream().map(
                    ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        } else if (exception instanceof MethodArgumentNotValidException) {
            userMessage = ((MethodArgumentNotValidException) exception).getAllErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        } else {
            userMessage = exception.getMessage();
        }
        errorDescription.setUserMessage(userMessage);
        errorDescription.setDevMessage(Optional.ofNullable(exception.getCause())
            .map(Throwable::getMessage)
            .orElse(exception.getMessage()));
        return errorDescription;
    }
}
