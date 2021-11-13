package ru.bank.validation.exception.model;

public class GatewayTimeoutException extends Throwable {

    public GatewayTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
