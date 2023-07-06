package com.stefanini.librarybackend.domain;

import com.fasterxml.jackson.annotation.*;

import com.stefanini.librarybackend.domain.enums.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
//@JsonIdentityReference(alwaysAsId = true)
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "shelfNumber")
    private String shelfNumber;

    @Column(name = "bookStatus")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @CreationTimestamp
    @Column(name = "createdOn")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "book-user")
    private User user;


    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_category", inverseJoinColumns = @JoinColumn(name = "category_id"), joinColumns = @JoinColumn(name = "book_id"))
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_author", inverseJoinColumns = @JoinColumn(name = "author_id"), joinColumns = @JoinColumn(name = "book_id"))
    private List<Author> authors = new ArrayList<>();


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "book-history")
    private List<History> history;

    public Book(int id, String title, String description, String shelfNumber, BookStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.shelfNumber = shelfNumber;
        this.status = status;
    }

    public Book(String title, String description, String shelfNumber, BookStatus status) {
        this.title = title;
        this.description = description;
        this.shelfNumber = shelfNumber;
        this.status = status;
    }

    public Book(String title, String description, String shelfNumber) {
        this.title = title;
        this.description = description;
        this.shelfNumber = shelfNumber;
    }

}