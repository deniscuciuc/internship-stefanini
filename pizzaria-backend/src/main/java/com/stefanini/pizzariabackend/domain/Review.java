package com.stefanini.pizzariabackend.domain;

import com.stefanini.pizzariabackend.domain.enums.ReviewMark;
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
@Entity(name = "Review")
@Table(name = "Review")
public class Review {

    @Id
    @SequenceGenerator(
            name = "review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "review_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "text",
            updatable = false
    )
    private String text;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            name = "mark",
            nullable = false,
            updatable = false
    )
    @Enumerated(STRING)
    private ReviewMark mark;

    @CreationTimestamp
    @Column(
            name = "created_at"
    )
    private LocalDateTime createdAt;
}
