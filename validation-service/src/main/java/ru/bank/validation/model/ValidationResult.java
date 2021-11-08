package ru.bank.validation.model;

import lombok.Data;

@Data
public class ValidationResult {
    Boolean isPersonInBlackList;
    Boolean isApproved;
}
