package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.domain.Author;
import com.stefanini.librarybackend.service.AuthorService;
import com.stefanini.librarybackend.service.impl.AuthorServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Author createAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Author updateAuthor(@PathVariable int id, @RequestBody Author author) {
        return authorService.update(id, author);
    }

    @PutMapping("/assignBook/{bookId}/{id}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Author assignBook(@PathVariable int bookId, @PathVariable int id) {
        return authorService.addBookToAuthor(bookId, id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public int deleteAuthor(@PathVariable int id) {
        return authorService.deleteAuthor(id);
    }

    @GetMapping("/authors/{pageNumber}/{pageSize}/{sortBy}/{sortOrder}")
    public List<Author> getAllPaginatedAndSortedAuthors(
            @PathVariable int pageNumber,
            @PathVariable int pageSize,
            @PathVariable String sortBy,
            @PathVariable String sortOrder
    ) {
        return authorService.getAllPaginatedAndSortedAuthors(pageNumber, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/all-authors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/numberOf")
    public Long getNumberOfAuthors() {
        return authorService.getNumberOfAuthors();
    }
}
