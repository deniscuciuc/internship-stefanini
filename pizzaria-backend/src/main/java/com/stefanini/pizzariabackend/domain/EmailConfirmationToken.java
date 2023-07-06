package com.stefanini.pizzariabackend.domain;

import com.stefanini.pizzariabackend.domain.enums.EmailConfirmationTokenStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "EmailConfirmationToken")
@Table(name = "Email_Confirmation_Token")
public class EmailConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "email_confirmation_token_sequence",
            sequenceName = "email_confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "email_confirmation_token_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "token",
            nullable = false,
            updatable = false
    )
    private String token;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;


    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(STRING)
    private EmailConfirmationTokenStatus status;
}
