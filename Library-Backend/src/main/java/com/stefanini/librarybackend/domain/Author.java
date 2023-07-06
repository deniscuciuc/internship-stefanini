package com.stefanini.librarybackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "biography", length = 1_000)
    private String biography;

    @JsonBackReference
    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author(int id, String firstName, String lastName, Date birthDate, String biography, List<Book> books) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setBiography(biography);
        setBooks(books);
    }

    public Author(int id, String firstName, String lastName, Date birthDate, String biography) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setBiography(biography);
    }

    public Author(String firstName, String lastName, Date birthDate, String biography, List<Book> books) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setBiography(biography);
        setBooks(books);
    }

    public Author(String firstName, String lastName, Date birthDate, String biography) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setBiography(biography);
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}