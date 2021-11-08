package ru.bank.validation.exception.model;

public class SalaryCheckDuplicatedException extends ServiceException {

    public SalaryCheckDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
