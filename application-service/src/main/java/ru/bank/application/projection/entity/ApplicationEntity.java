package ru.bank.application.projection.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bank.common.events.Status;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "application_view")
@Builder
public class ApplicationEntity {

    @Id
    private String id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private Integer age;
    private Long inn;
    private String email;
    private Long phone;
    @Column(name = "loan_amount_request")
    private Long loanAmountRequested;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "loan_amount_approved")
    private Long loanAmountApproved;
}
