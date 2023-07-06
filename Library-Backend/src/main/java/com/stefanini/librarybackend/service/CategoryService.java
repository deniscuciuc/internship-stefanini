package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Category;

import java.util.List;

/**
 * This interface is for managing Category entity in database.
 * CategoryService can execute operations with Author entity such as creating, updating, deleting and others.
 *
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface CategoryService {

    /**
     * Create new category.
     *
     * @param category that should be created in controller before calling this method
     * @return the category that was saved in database
     */
    Category addCategory(Category category);


    /**
     * Delete category from database by id.
     *
     * @param id of category to be deleted
     * @return id of category that was deleted
     */
    int deleteCategory(int id);


    /**
     * Gets from database all categories.
     *
     * @return list of all categories from database
     */
    List<Category> getAllCategories();


    /**
     * This method assigns the book to the category using the id of both entities.
     *
     * @param bookId that need ot assign
     * @param id     of category which need to assign a book
     * @return category with assigned book
     */
    Category addBookToCategory(int bookId, int id);


    /**
     * Get category by id.
     *
     * @param id of category to be found
     * @return category that was found
     */
    Category getCategoryById(int id);
}
