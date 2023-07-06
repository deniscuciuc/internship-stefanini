package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Book;

import java.util.List;

/**
 * This interface is for managing Book entity in database.
 * BookService can execute operations with Book entity such as creating, updating, deleting and other crud operations.
 * BookService can execute business value operations such as: reserving books, returning books, give the book, finding by any criteria
 *
 * @author cirligelena
 * @version 0.1
 * @since 0.1
 */
public interface BookService {

    /**
     * Create new book.
     *
     * @param book
     * @return created book with status AVAILABLE
     */
    Book addBook(Book book);

    /**
     * Return sorted and only needed books.
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @return pageNumber of books sorted by sortBy for page pageNumber
     */
    List<Book> getAllBooks(int pageNumber, int pageSize, String sortBy, String sortOrder);

    /**
     * Update book data.
     *
     * @param id   of book to be updated
     * @param book with updated data
     * @return updated book
     */
    Book update(int id, Book book);

    /**
     * Find and return book by id.
     *
     * @param id of book to be found
     * @return book that was found
     */
    Book findById(int id);

    /**
     * Delete book by id.
     *
     * @param id of book to be deleted
     * @return id of book that was deleted
     */
    int deleteBook(int id);

    /**
     * Reserve the book for user.
     * Then save changes of both entities in database.
     *
     * @param bookId to be reserved
     * @param userId that want to reserve book
     * @return book with updated user and status BOOKED
     */
    Book bookTheBook(int bookId, int userId);

    /**
     * Give the book for user (this means that user takes the book physically).
     * Then save updated book status in database.
     *
     * @param bookId to be given
     * @param userId that want to take the book
     * @return book with updated status TAKEN
     */
    Book giveTheBook(int bookId, int userId);


    /**
     * Return book (this means that user returns the book physically in library).
     *
     * @param bookId to be returned
     * @return book with updated status AVAILABLE
     */
    Book returnTheBook(int bookId);


    /**
     * Find and return book by eny criteria (title and description).
     *
     * @param criteria by which the book will be searched for
     * @return list of books found by criteria
     */
    List<Book> findBooksByAnyCriteria(String criteria);

    /**
     * Get all books by category.
     *
     * @param categoryId by which the book will be searched for
     * @return list of categories found by category id
     */
    List<Book> getBookByCategory(int categoryId);

    /**
     * Get all books by authors.
     *
     * @param authorId by which the book will be searched for
     * @return list of books found by author id
     */
    List<Book> findBooksByAuthor(int authorId);

    /**
     * Create new book with existing category and author by id of both.
     *
     * @param book to be created
     * @param categoryId to be assigned to book
     * @param authorId   to be assigned to book
     * @return created book with category and author
     */
    Book addBookWithExistingCategoryAndAuthor(Book book, int categoryId, int authorId);

    /**
     * Return number of books.
     *
     * @return long number of books
     */
    Long getNumberOfBooks();
}
