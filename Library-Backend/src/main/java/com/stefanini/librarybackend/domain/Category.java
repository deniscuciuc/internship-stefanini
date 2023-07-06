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
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", unique = true)
    private String title;

    @JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private List<Book> books = new ArrayList<>();

    public Category(int id, String title, List<Book> books) {
        setId(id);
        setTitle(title);
        setBooks(books);
    }

    public Category(int id, String title) {
        setId(id);
        setTitle(title);
    }

    public Category(String title, List<Book> books) {
        setTitle(title);
        setBooks(books);
    }

    public Category(String title) {
        setTitle(title);
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getCategories().add(this);
    }
}