package com.stefanini.pizzariabackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Post")
@Table(name = "Post")
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "post_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            length = 500,
            nullable = false
    )
    private String title;

    @Column(
            name = "summary",
            length = 1000
    )
    private String summary;

    @Column(
            name = "text",
            length = 10000,
            nullable = false
    )
    private String text;

    @ManyToOne
    @JoinColumn(
            name = "user-id"
    )
    private User author;

    @Lob
    @Column(
            name = "image"
    )
    private byte[] image;
}
