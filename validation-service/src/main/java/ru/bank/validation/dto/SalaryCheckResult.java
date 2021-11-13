package ru.bank.validation.dto;

public record SalaryCheckResult(String applicationId, Boolean isApproved,
                                Boolean isCancelled, Long approvedAmount) {

}
