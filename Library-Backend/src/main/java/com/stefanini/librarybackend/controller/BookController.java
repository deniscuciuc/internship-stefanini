package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.service.AuthorService;
import com.stefanini.librarybackend.service.BookService;
import com.stefanini.librarybackend.service.impl.BookServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookServiceImpl bookServiceImpl, AuthorService authorService) {
        this.bookService = bookServiceImpl;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public int deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/books/{pageNumber}/{pageSize}/{sortBy}/{sortOrder}")
    public List<Book> getAllBooks(
            @PathVariable int pageNumber,
            @PathVariable int pageSize,
            @PathVariable String sortBy,
            @PathVariable String sortOrder
    ) {
        return bookService.getAllBooks(pageNumber, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/numberOf")
    public Long getNumberOfBooks() {
        return bookService.getNumberOfBooks();
    }

    @PutMapping("/bookTheBook/{bookId}/{userId}")
    @PreAuthorize("hasAnyAuthority('USER','LIBRARIAN', 'ADMIN')")
    public Book bookTheBook(@PathVariable int bookId, @PathVariable int userId) {
        return bookService.bookTheBook(bookId, userId);
    }

    @PutMapping("/giveTheBook/{bookId}/{userId}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Book giveTheBook(@PathVariable int bookId, @PathVariable int userId) {
        return bookService.giveTheBook(bookId, userId);
    }

    @PutMapping("/returnTheBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Book returnTheBook(@PathVariable int bookId) {
        return bookService.returnTheBook(bookId);
    }

    @GetMapping("/find_books_by_criteria/{criteria}")
    public List<Book> findBooksByCriteria(@PathVariable String criteria) {
        return bookService.findBooksByAnyCriteria(criteria);
    }

    @GetMapping("/bookByAuthor/{authorId}")
    public List<Book> findBooksByAuthorId(@PathVariable int authorId) {
        return bookService.findBooksByAuthor(authorId);
    }

    @GetMapping("/bookByCategory/{categoryId}")
    public List<Book> getBooksByCategory(@PathVariable int categoryId) {
        return bookService.getBookByCategory(categoryId);
    }

    @PostMapping("/addBookWithExistingCategoryAndAuthor/{categoryId}/{authorId}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Book addBookWithExistingCategoryAndAuthor(@RequestBody Book book, @PathVariable int categoryId, @PathVariable int authorId) {
        return bookService.addBookWithExistingCategoryAndAuthor(book, categoryId, authorId);
    }
}