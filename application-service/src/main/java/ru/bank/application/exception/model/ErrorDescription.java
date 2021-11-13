package ru.bank.application.exception.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDescription {

    String serviceName;
    String errorCode;
    String userMessage;
    String devMessage;
}
