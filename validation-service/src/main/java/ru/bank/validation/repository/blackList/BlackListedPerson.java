package ru.bank.validation.repository.blackList;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class BlackListedPerson {

    @Id
    private Long id;

    private String name;

    private String lastName;

    private Long inn;
}
