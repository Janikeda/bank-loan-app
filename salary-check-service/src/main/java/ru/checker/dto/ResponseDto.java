package ru.checker.dto;

public record ResponseDto(String applicationId, Boolean isApproved,
                          Boolean isCancelled, Long approvedAmount) {

}
