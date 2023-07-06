package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Author;

import java.util.List;

/**
 * This interface is for managing Author entity in database.
 * AuthorService can execute operations with Author entity such as creating, updating, deleting and others.
 *
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface AuthorService {

    /**
     * Create new author.
     *
     * @param author that should be created in controller before calling this method
     * @return the author that was saved in database
     */
    Author addAuthor(Author author);


    /**
     * Perform an updating/editing operation about author and saving it.
     *
     * @param id     of author to be updated/edited
     * @param author object with new data that should be saved
     * @return the author with updated data
     */
    Author update(int id, Author author);


    /**
     * Delete author by id.
     *
     * @param id of author to be deleted
     * @return id of author that was deleted
     */
    int deleteAuthor(int id);


    /**
     * Gets paginated and sorted authors.
     *
     * @param pageNumber current page
     * @param pageSize   how many authors can be on page
     * @param sortBy     one of authors field
     * @param sortOrder  asc or desc
     * @return
     */
    List<Author> getAllPaginatedAndSortedAuthors(int pageNumber, int pageSize, String sortBy, String sortOrder);


    /**
     * This method assigns the book to the author using the id of both entities.
     * Then they will be saved in database.
     *
     * @param bookId that need to assign
     * @param id     of author which need to assign a book
     * @return author with assigned book
     */
    Author addBookToAuthor(int bookId, int id);

    /**
     * Return number of authors.
     *
     * @return long number of authors
     */
    Long getNumberOfAuthors();

    /**
     * Gets all authors.
     *
     * @return list of all authors
     */
    List<Author> getAllAuthors();
}
