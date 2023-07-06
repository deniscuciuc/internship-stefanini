package com.stefanini.pizzariabackend.domain;

import com.stefanini.pizzariabackend.domain.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "User")
@Table(
        name = "User",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_unique",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "user_nickname_unique",
                        columnNames = "nickname"
                )
        }
)
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence"
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "email",
            nullable = false,
            updatable = false
    )
    private String email;

    @Column(
            name = "nickname",
            nullable = false,
            updatable = false
    )
    private String nickname;


    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @CreationTimestamp
    @Column(
            name = "created_at"
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(
            name = "last_updated"
    )
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "user")
    private List<History> history = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @UpdateTimestamp
    @Column(
            name = "last_review_wrote_at"
    )
    private LocalDateTime lastReviewWroteAt;


    @ElementCollection(
            targetClass = Role.class,
            fetch = FetchType.EAGER
    )
    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id"
            )
    )
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(
            name = "is_confirmed_by_email"
    )
    private boolean isConfirmedByEmail;

    public User(Long id, String email, String nickname, String password, List<History> histories) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.history = histories;
    }
    public User(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
