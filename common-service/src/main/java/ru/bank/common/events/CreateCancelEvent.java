package ru.bank.common.events;

import lombok.Data;

@Data
public class CreateCancelEvent {

    private final String applicationId;
}
