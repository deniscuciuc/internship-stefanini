package com.stefanini.librarybackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stefanini.librarybackend.domain.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "id", nullable = false)
    private int id;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "book-user")
    private List<Book> book;


    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "user-history")
    private List<History> history;


    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-confirmationTokens")
    private List<ConfirmationToken> confirmationTokens;

    @Column(name = "isConfirmedByEmail", columnDefinition = "boolean default false")
    private boolean isConfirmedByEmail;

    @Column(name = "hasTemporaryPassword", columnDefinition = "boolean default false")
    private boolean hasTemporaryPassword;

    public User(int id, String email, String password) {
        setId(id);
        setEmail(email);
        setPassword(password);
    }

    public User(String email, String password, Profile profile) {
        setId(id);
        setEmail(email);
        setPassword(password);
        setProfile(profile);
    }

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, Profile profile) {
        this.email = email;
        this.profile = profile;
    }
}
