package ru.bank.application.exception;

import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorExceptionBuilder errorExceptionBuilder;

    public ExceptionHandler(ErrorExceptionBuilder errorExceptionBuilder) {
        this.errorExceptionBuilder = errorExceptionBuilder;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleApplicationException(RuntimeException e) {
        return handleExceptionInternal(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {
        return handleExceptionInternal(HttpStatus.BAD_REQUEST, e);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        @NonNull MethodArgumentNotValidException e, @NonNull HttpHeaders headers,
        @NonNull HttpStatus status,
        @NonNull WebRequest request) {
        return handleExceptionInternal(HttpStatus.BAD_REQUEST, e);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception e, Object body,
        @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.error("Unhandled exception caught", e);
        return handleExceptionInternal(status, e);
    }


    private ResponseEntity<Object> handleExceptionInternal(HttpStatus status, Exception e) {
        return ResponseEntity.status(status)
            .body(errorExceptionBuilder.build(status, e));
    }

}
