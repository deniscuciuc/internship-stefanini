package com.stefanini.librarybackend.dao;

import java.util.List;

/**
 * The class is responsible for database operations with Book entity.
 *
 * @param <Book>>
 * @author cirligelena
 * @version 0.1
 * @since 0.1
 */
public interface BookDAO<Book> extends IGenericDao<Book> {

    /**
     * Get books by any criteria from database.
     *
     * @param criteria
     * @return list of books from database
     */
    List<Book> getBooksByAnyCriteria(String criteria);

}
