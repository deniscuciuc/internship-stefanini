package com.stefanini.pizzariabackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "History")
@Table(name = "History")
public class History {

    @Id
    @SequenceGenerator(
            name = "history_sequence",
            sequenceName = "history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "history_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "order",
            nullable = false,
            updatable = false
    )
    private String actionName;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    @Column(
            name = "performed_at",
            nullable = false,
            updatable = false
    )
    @CreationTimestamp
    private LocalDate performedAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    public History(String actionName, LocalDate performedAt) {
        this.actionName = actionName;
        this.performedAt = performedAt;
    }

    public History(Long id, String actionName, LocalDate performedAt) {
        this.id = id;
        this.actionName = actionName;
        this.performedAt = performedAt;
    }
}
