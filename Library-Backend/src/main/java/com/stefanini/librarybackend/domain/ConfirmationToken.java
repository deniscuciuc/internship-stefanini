package com.stefanini.librarybackend.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stefanini.librarybackend.domain.enums.ConfirmationTokenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.stefanini.librarybackend.domain.enums.ConfirmationTokenStatus.*;

@Entity
@Table(name = "confirmation_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "createdAt", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "expiresAt", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "confirmedAt")
    private LocalDateTime confirmedAt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ConfirmationTokenStatus status;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    @JsonBackReference(value = "user-confirmationTokens")
    private User user;

    private ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
                              User user, ConfirmationTokenStatus status) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
        this.status = status;
    }

    public static ConfirmationToken createConfirmationToken(String token, User user) {
        LocalDateTime fifteenMinutesLater = LocalDateTime.now().plusMinutes(15);
        return new ConfirmationToken(
                token,
                LocalDateTime.now(),
                fifteenMinutesLater,
                user,
                PENDING_CONFIRMATION
        );
    }
}
